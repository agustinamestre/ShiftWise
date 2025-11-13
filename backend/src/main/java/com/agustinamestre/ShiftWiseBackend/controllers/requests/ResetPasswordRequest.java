package com.agustinamestre.ShiftWiseBackend.controllers.requests;

import com.agustinamestre.ShiftWiseBackend.shared.validators.password.Password;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ResetPasswordRequest {
    @NotBlank(message = "El token es requerido")
    private String token;

    @Password
    private String newPassword;
}
