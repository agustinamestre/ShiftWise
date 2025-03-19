package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.domain.LoginAttempt;
import com.agustinamestre.ShiftWiseBackend.repositories.LoginAttemptRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoginServiceImpl {

    private final LoginAttemptRepository loginAttemptRepository;

    @Autowired
    public LoginServiceImpl(LoginAttemptRepository loginAttemptRepository) {
        this.loginAttemptRepository = loginAttemptRepository;
    }

    @Transactional
    public void addLoginAttempt(String nroDocumento, boolean success) {

        LoginAttempt loginAttempt = new LoginAttempt(nroDocumento, success);
        loginAttemptRepository.save(loginAttempt);
    }

    public List<LoginAttempt> findRecentLoginAttempts(String nroDocumento) {
        return loginAttemptRepository.findTop10ByNroDocumentoOrderByCreatedAtDesc(nroDocumento);
    }

}
