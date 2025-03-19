package com.agustinamestre.ShiftWiseBackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "login_attempt")
public class LoginAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nroDocumento;

    @Column(nullable = false)
    private boolean success;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public LoginAttempt(String nroDocumento, boolean success) {
        this.nroDocumento = nroDocumento;
        this.success = success;
    }
}
