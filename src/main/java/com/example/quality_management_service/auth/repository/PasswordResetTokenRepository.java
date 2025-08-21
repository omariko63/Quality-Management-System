package com.example.quality_management_service.auth.repository;


import com.example.quality_management_service.auth.model.PasswordResetToken;
import com.example.quality_management_service.management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUser(User user);
}

