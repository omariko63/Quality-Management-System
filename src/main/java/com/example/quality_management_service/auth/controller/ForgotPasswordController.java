package com.example.quality_management_service.auth.controller;

import com.example.quality_management_service.auth.dto.EmailRequest;
import com.example.quality_management_service.auth.dto.PasswordRequest;
import com.example.quality_management_service.auth.service.ForgotPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/verifyMail")
    public ResponseEntity<String> verifyEmail(@RequestBody EmailRequest request) {
        return forgotPasswordService.sendOtpToEmail(request.email());
    }

    @PostMapping("/verifyOtp/{otp}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @RequestBody EmailRequest request) {
        return forgotPasswordService.verifyOtp(request.email(), otp);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPasswordHandler(@RequestBody PasswordRequest passwordRequest) {
        return forgotPasswordService.resetPassword(passwordRequest);
    }
}
