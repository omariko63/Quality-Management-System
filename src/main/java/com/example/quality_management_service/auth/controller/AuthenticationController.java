package com.example.quality_management_service.auth.controller;

import com.example.quality_management_service.auth.dto.LoginRequestDTO;
import com.example.quality_management_service.auth.dto.Token;
import com.example.quality_management_service.auth.service.AuthenticationService;
import com.example.quality_management_service.auth.service.CustomUserDetailsService;
import com.example.quality_management_service.auth.util.InMemoryTokenStore;
import com.example.quality_management_service.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Token token = authenticationService.login(
                loginRequestDTO.username(),
                loginRequestDTO.password()
        );

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // store access token in memory
        InMemoryTokenStore.storeToken(token.accessToken());

        return ResponseEntity.ok(token);
    }

    // Logout endpoint (using Authorization header instead of body)
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest().body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        if (!InMemoryTokenStore.isTokenValid(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is already invalid or expired");
        }

        InMemoryTokenStore.invalidateToken(token);
        return ResponseEntity.ok("Logged out successfully");
    }

    // Refresh token endpoint
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refresh_token");
        if (refreshToken == null) {
            return ResponseEntity.badRequest().body("Missing refresh_token");
        }

        String username;
        try {
            username = jwtUtil.extractUsername(refreshToken);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }

        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("User not found");
        }

        if (!jwtUtil.isRefreshTokenValid(refreshToken, user)) {
            return ResponseEntity.status(401).body("Invalid or expired refresh token");
        }

        String role = jwtUtil.extractRole(refreshToken);
        try {
            String newAccessToken = jwtUtil.generateAccessToken(username, role);
            String newRefreshToken = jwtUtil.generateRefreshToken(username, role);

            // store new access token
            InMemoryTokenStore.storeToken(newAccessToken);

            return ResponseEntity.ok(Map.of(
                    "access_token", newAccessToken,
                    "refresh_token", newRefreshToken
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Token generation error");
        }
    }
}
