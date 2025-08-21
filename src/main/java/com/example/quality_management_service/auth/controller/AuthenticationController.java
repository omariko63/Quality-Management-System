package com.example.quality_management_service.auth.controller;

import com.example.quality_management_service.auth.dto.LoginRequestDTO;
import com.example.quality_management_service.auth.dto.Token;
import com.example.quality_management_service.auth.service.AuthenticationService;
import com.example.quality_management_service.auth.service.CustomUserDetailsService;
import com.example.quality_management_service.auth.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        System.out.println("login");
        Token token = authenticationService.login(
                loginRequestDTO.username(),
                loginRequestDTO.password()
        );
        if (token == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refresh_token");
        System.out.println("[Refresh] Received refresh token: " + refreshToken);
        if (refreshToken == null) {
            System.out.println("[Refresh] Missing refresh_token");
            return ResponseEntity.badRequest().body("Missing refresh_token");
        }
        String username;
        try {
            username = jwtUtil.extractUsername(refreshToken);
            System.out.println("[Refresh] Extracted username: " + username);
        } catch (Exception e) {
            System.out.println("[Refresh] Invalid refresh token: " + e.getMessage());
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
        UserDetails user;
        try {
            user = userDetailsService.loadUserByUsername(username);
            System.out.println("[Refresh] Loaded user: " + user.getUsername());
        } catch (Exception e) {
            System.out.println("[Refresh] User not found: " + e.getMessage());
            return ResponseEntity.status(401).body("User not found");
        }
        if (!jwtUtil.isRefreshTokenValid(refreshToken, user)) {
            System.out.println("[Refresh] Invalid or expired refresh token");
            return ResponseEntity.status(401).body("Invalid or expired refresh token");
        }
        String role = jwtUtil.extractRole(refreshToken);
        System.out.println("[Refresh] Extracted role: " + role);
        try {
            String newAccessToken = jwtUtil.generateAccessToken(username, role);
            String newRefreshToken = jwtUtil.generateRefreshToken(username, role);
            System.out.println("[Refresh] Generated new tokens");
            return ResponseEntity.ok(Map.of(
                    "access_token", newAccessToken,
                    "refresh_token", newRefreshToken
            ));
        } catch (Exception e) {
            System.out.println("[Refresh] Error generating tokens: " + e.getMessage());
            return ResponseEntity.status(500).body("Token generation error");
        }
    }
}
