package com.example.quality_management_service.management.repository;

import com.example.quality_management_service.management.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.passwordHash = :password WHERE u.email = :email")
    void updatePassword(@Param("email") String email, @Param("password") String password);

}
