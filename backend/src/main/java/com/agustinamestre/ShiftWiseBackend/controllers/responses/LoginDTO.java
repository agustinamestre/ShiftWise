package com.agustinamestre.ShiftWiseBackend.controllers.responses;

import lombok.Data;

@Data
public class LoginDTO {

    String nroDocumento;

    String token;

    public LoginDTO(String nroDocumento, String token) {
        this.nroDocumento = nroDocumento;
        this.token = token;
    }
}