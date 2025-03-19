package com.agustinamestre.ShiftWiseBackend.models.error;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Log4j2
@Getter
public class ApiErrorResponse {

    private String uri;

    private LocalDateTime timestamp;

    private List<ApiError> error;

    private int errorCode;

    private String description;

    public ApiErrorResponse(String uri, LocalDateTime timestamp, List<ApiError> error) {
        this.uri = uri;
        this.timestamp = timestamp;
        this.error = error;
    }

    public ApiErrorResponse(int errorCode, String description) {
        this.errorCode = errorCode;
        this.description = description;
    }

    public static ApiErrorResponse crearResponseConError(String uri, ApiError error) {
        logError(error);
        return new ApiErrorResponse(uri, LocalDateTime.now(), List.of(error));
    }

    public static ApiErrorResponse crearResponseConListaDeErrores(String uri, List<ApiError> errores) {
        errores.forEach(ApiErrorResponse::logError);
        return new ApiErrorResponse(uri, LocalDateTime.now(), errores);
    }

    private static void logError(ApiError error) {
        if (nonNull(error.getLogMessage())) {
            log.error("Ocurrio un error al procesar la solicitud:");
            log.error(error.getLogMessage());
        }
    }
}
