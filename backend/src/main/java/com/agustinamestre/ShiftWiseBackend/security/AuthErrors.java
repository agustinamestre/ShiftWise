package com.agustinamestre.ShiftWiseBackend.security;

import com.agustinamestre.ShiftWiseBackend.models.error.ApiError;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthErrors {

    public static final ApiError ACCESO_DENEGADO = new ApiError("accesoDenegado", "No tiene autorización para acceder a este recurso.");
    public static final ApiError ACCESO_PROHIBIDO = new ApiError("accesoProhibido", "El token proporcionado no es válido o no tiene los permisos suficientes para acceder a este recurso. Verifique su token o solicite permisos adicionales.");
    public static final ApiError SIN_PERMISOS_SUFICIENTES = new ApiError("permisosInsuficientes", "No tiene los permisos suficientes para realizar esta acción.");
    public static final ApiError TOKEN_EXPIRADO = new ApiError("tokenExpirado", "El token proporcionado se encuentra expirado.");

}
