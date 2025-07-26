package com.agustinamestre.ShiftWiseBackend.security;

public class SecurityConstant {

    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 1; // 1 h
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String AUTHORITIES = "authorities";
    public static final String TOKEN_NO_SE_PUEDE_VERIFICAR = "El Token no se puede verificar";
    public static final String API_SHIFTWISE = "ShiftWise";
    public static final String[] AUTH_PUBLIC_WHITELIST = {
            "/shiftwise/v1/**"
    };

}
