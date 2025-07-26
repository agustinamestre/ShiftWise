package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.exceptions.UnauthorizedException;
import com.agustinamestre.ShiftWiseBackend.repositories.UserRepository;
import com.agustinamestre.ShiftWiseBackend.security.JWTTokenProvider;
import com.agustinamestre.ShiftWiseBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

@RestController
@RequestMapping("/shiftwise/v1/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JWTTokenProvider jwtTokenProvider;


    @Autowired
    public AuthController(UserRepository userRepository, JWTTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
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

        var usuario = userRepository.findByNroDocumentoAndPassword(documento, password)
                .orElseThrow(() -> new UnauthorizedException("Usuario o contraseña inválidos"));

        String jwtToken = jwtTokenProvider.generateJwtToken(usuario);

        return ResponseEntity.noContent()
                .header("X-Auth-Token", jwtToken)
                .build();
    }
}
