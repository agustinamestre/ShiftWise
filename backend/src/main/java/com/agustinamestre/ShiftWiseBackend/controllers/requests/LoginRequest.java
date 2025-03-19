package com.agustinamestre.ShiftWiseBackend.controllers.requests;

import com.agustinamestre.ShiftWiseBackend.shared.validators.documento.Documento;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class LoginRequest {

    @Documento
    String nroDocumento;

    @NotBlank(message = "La contraseña es requerida.")
    String password;
}
