package com.agustinamestre.ShiftWiseBackend.controllers.requests;

import com.agustinamestre.ShiftWiseBackend.shared.validators.email.Email;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForgotPasswordRequest {
    @Email(message = "El correo debe ser válido")
    private String email;
}
