package com.agustinamestre.ShiftWiseBackend.models.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShiftWiseErrors {

    public static final ApiError DOCUMENTO_EXISTENTE = new ApiError("DocumentoExistente","Ya existe un empleado con el documento ingresado.");
    public static final ApiError EMAIL_EXISTENTE = new ApiError("EmailExistente","Ya existe un empleado con el email ingresado.");

}
