package com.example.quality_management_service.auth.model;

import com.example.quality_management_service.management.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/** Entity for managing password reset OTPs */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPassword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fpid;

    @Column(nullable = false)
    private Integer otp;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTime;

    @Column(nullable = false)
    @Builder.Default
    private boolean verified = false;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}
