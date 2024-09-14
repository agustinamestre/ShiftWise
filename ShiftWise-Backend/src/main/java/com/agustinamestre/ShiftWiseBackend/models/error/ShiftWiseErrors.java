package com.agustinamestre.ShiftWiseBackend.models.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ShiftWiseErrors {
    public static final String CODE_ERROR_TECNICO = "ErrorTecnico";
    public static final String CAMPO_CON_VALOR_INVALIDO = "Error en el campo {0}: {1}";
    public static final String CAMPO_REQUERIDO = "El campo {0} es requerido y no fue informado";
    public static final ApiError DOCUMENTO_EXISTENTE = new ApiError("DocumentoExistente","Ya existe un empleado con el documento ingresado.");
    public static final ApiError EMAIL_EXISTENTE = new ApiError("EmailExistente","Ya existe un empleado con el email ingresado.");
    public static final ApiError EMPLEADO_NOT_FOUND = new ApiError("EmpleadoNoEncontrado","No se encontró el empleado indicado.");
}
