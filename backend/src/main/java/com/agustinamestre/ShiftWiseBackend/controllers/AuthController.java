package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.ForgotPasswordRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.requests.ResetPasswordRequest;
import com.agustinamestre.ShiftWiseBackend.exceptions.UnauthorizedException;
import com.agustinamestre.ShiftWiseBackend.security.JWTTokenProvider;
import com.agustinamestre.ShiftWiseBackend.services.UserService;
import com.agustinamestre.ShiftWiseBackend.services.impl.PasswordResetServiceImpl;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/shiftwise/v1/auth")
public class AuthController {

    private final UserService userService;
    private final JWTTokenProvider jwtTokenProvider;
    private final PasswordResetServiceImpl passwordResetService;

    @Autowired
    public AuthController(UserService userService, JWTTokenProvider jwtTokenProvider, PasswordResetServiceImpl passwordResetService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestHeader("Authorization") String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")){
            throw new UnauthorizedException("Header autenticación invalido");
        }

        String base64Credenciales = authorizationHeader.substring("Basic ".length());

        byte[] decodedBytesCredenciales = Base64.getDecoder().decode(base64Credenciales);
        String decodedCredenciales = new String(decodedBytesCredenciales);

        String[] partes = decodedCredenciales.split(":", 2);
        if (partes.length !=2){
            throw new UnauthorizedException("Credenciales invalidas");
        }

        String documento = partes[0];
        String password = partes[1];

        var usuario = userService.obtenerUsuario(documento, password);

        String jwtToken = jwtTokenProvider.generateJwtToken(usuario);

        return ResponseEntity.noContent()
                .header("X-Auth-Token", jwtToken)
                .build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequest request) throws MessagingException {
        passwordResetService.requestPasswordReset(request.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody ResetPasswordRequest request) {
        passwordResetService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }

}
