package com.agustinamestre.ShiftWiseBackend.controllers.advice;

import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.models.error.ApiErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public ApiErrorResponse handleBusinessException(BusinessException ex, ServletWebRequest request) {
        return ApiErrorResponse.crearResponseConError(request.getRequest().getRequestURI(), ex.getApiError());
    }
}
