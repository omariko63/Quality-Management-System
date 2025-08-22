package com.example.quality_management_service.auth.service;

import com.example.quality_management_service.auth.dto.MailBody;
import com.example.quality_management_service.auth.exception.InvalidOtpException;
import com.example.quality_management_service.auth.exception.OtpExpiredException;
import com.example.quality_management_service.auth.model.ForgotPassword;
import com.example.quality_management_service.auth.repository.ForgotPasswordRepository;
import com.example.quality_management_service.auth.dto.PasswordRequest;
import com.example.quality_management_service.management.model.User;
import com.example.quality_management_service.management.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> sendOtpToEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        int otp = generateOtp();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .subject("OTP for Forgot Password request")
                .text("Your OTP for resetting password is: " + otp + "\nThis OTP is valid for 70 seconds.")
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .verified(false)
                .user(user)
                .build();

        forgotPasswordRepository.findByUser(user).ifPresentOrElse(existing -> {
            existing.setOtp(otp);
            existing.setExpirationTime(new Date(System.currentTimeMillis() + 70 * 1000));
            existing.setVerified(false);
            forgotPasswordRepository.save(existing);
        }, () -> forgotPasswordRepository.save(fp));

        emailService.sendSimpleMessage(mailBody);
        return ResponseEntity.ok("OTP has been sent to your email");
    }

    public ResponseEntity<String> verifyOtp(String email, Integer otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp, user)
                .orElseThrow(() -> new InvalidOtpException("Invalid OTP"));

        if (fp.getExpirationTime().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            throw new OtpExpiredException("OTP has expired");
        }

        fp.setVerified(true);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("OTP verified successfully!");
    }

    @Transactional
    public ResponseEntity<String> resetPassword(PasswordRequest changePassword) {
        User user = userRepository.findByEmail(changePassword.email())
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        ForgotPassword fp = forgotPasswordRepository.findByUser(user)
                .orElseThrow(() -> new InvalidOtpException("No OTP request found for this user"));

        if (!fp.isVerified()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Please verify OTP before changing password");
        }

        if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
            return ResponseEntity.badRequest().body("Passwords do not match");
        }

        String encodedPassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(changePassword.email(), encodedPassword);

        user.setForgetPassword(null);
        forgotPasswordRepository.deleteById(fp.getFpid());

        return ResponseEntity.ok("Password has been changed successfully!");
    }

    private Integer generateOtp() {
        Random random = new Random();
        return random.nextInt(100_000, 999_999);
    }
}
