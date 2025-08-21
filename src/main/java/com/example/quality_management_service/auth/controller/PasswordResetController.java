package com.example.quality_management_service.auth.controller;

import com.example.quality_management_service.auth.dto.PasswordResetConfirmDTO;
import com.example.quality_management_service.auth.dto.PasswordResetRequestDTO;
import com.example.quality_management_service.management.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/reset-password")
public class PasswordResetController {

    private final UserService userService;

    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    // Step 1: Request password reset
    @PostMapping("/request")
    public ResponseEntity<String> requestReset(@RequestBody PasswordResetRequestDTO request) {
        String token = userService.generateResetToken(request.email());

        // TODO: send token by email instead of returning
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Password reset requested. Token: " + token);
    }

    // Step 2: Confirm reset with token + new password
    @PostMapping("/confirm")
    public ResponseEntity<String> confirmReset(@RequestBody PasswordResetConfirmDTO request) {
        userService.resetPassword(request.token(), request.newPassword());
        return ResponseEntity.ok("Password successfully reset.");
    }
}
