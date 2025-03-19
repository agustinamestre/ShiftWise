package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.LoginRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.LoginDTO;
import com.agustinamestre.ShiftWiseBackend.helper.JwtHelper;
import com.agustinamestre.ShiftWiseBackend.services.impl.LoginServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shiftwise/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final LoginServiceImpl loginService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, LoginServiceImpl loginService) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginDTO> login(@Valid @RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getNroDocumento(), request.getPassword()));
        } catch (BadCredentialsException e) {
            loginService.addLoginAttempt(request.getNroDocumento(), false);
            throw e;
        }

        String token = JwtHelper.generateToken(request.getNroDocumento());
        loginService.addLoginAttempt(request.getNroDocumento(), true);
        return ResponseEntity.ok(new LoginDTO(request.getNroDocumento(), token));
    }
}
