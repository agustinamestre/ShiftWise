package com.agustinamestre.ShiftWiseBackend.controllers.advice;

import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.models.error.ApiError;
import com.agustinamestre.ShiftWiseBackend.models.error.ApiErrorResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import static com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Log4j2
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    public ApiErrorResponse globalExceptionHandler(Exception ex, ServletWebRequest request) {
        ApiError apiError = new ApiError(CODE_ERROR_TECNICO, getGenericErrorMessageAndCause(ex), ex.getMessage());
        return ApiErrorResponse.crearResponseConError(request.getRequest().getRequestURI(), apiError);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse runtimeExceptionHandler(RuntimeException ex, ServletWebRequest request) {
        var apiError = new ApiError(CODE_ERROR_TECNICO, getGenericErrorMessageAndCause(ex), ex.getMessage());
        return ApiErrorResponse.crearResponseConError(request.getRequest().getRequestURI(), apiError);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handlerMethodValidationExceptionHandler(HandlerMethodValidationException ex, ServletWebRequest request) {

        final String requestURI = request.getRequest().getRequestURI();
        log.error("[REQUEST INVALIDO] Se interrumpe el procesamiento de la petición: " + request.getRequest().getMethod() + " " + requestURI);
        var errors = ex.getAllErrors().stream()
                .map(error -> {
                    if (error instanceof DefaultMessageSourceResolvable resolvable) {
                        final String field = ((DefaultMessageSourceResolvable) Objects.requireNonNull(resolvable.getArguments())[0]).getDefaultMessage();
                        return new ApiError(
                                field + "_invalido",
                                error.getDefaultMessage()
                        );
                    } else {
                        return new ApiError("ErrorTecnico", error.getDefaultMessage());
                    }
                }).toList();

        return ApiErrorResponse.crearResponseConListaDeErrores(requestURI, errors);
    }

    //Errores de Validaciones de los request con la anotacion ´@Valid´
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiErrorResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, ServletWebRequest request) {

        final String requestURI = request.getRequest().getRequestURI();
        List<ApiError> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        final String field = fieldError.getField();
                        if (NotNull.class.getSimpleName().equals(error.getCode())) {
                            // @NotNull
                            return new ApiError(field + "_requerido", MessageFormat.format(CAMPO_REQUERIDO, field));
                        } else if (NotBlank.class.getSimpleName().equals(error.getCode())) {
                            // @NotBlank
                            return new ApiError(field + "_requerido", MessageFormat.format(CAMPO_REQUERIDO, field));
                        } else {
                            // Custom annotation u otras
                            return new ApiError(field + "_invalido", error.getDefaultMessage());
                        }
                    } else {
                        // ViolationObjectError para las validaciones de @Notation que son a nivel clase.
                        return new ApiError(error.getCode(), error.getDefaultMessage());
                    }
                })
                .toList();

        return errors.stream()
                .filter(error -> error.getErrorMessage().contains("lista"))
                .findFirst()
                .map(apiError -> ApiErrorResponse.crearResponseConError(requestURI, apiError))
                .orElseGet(() -> ApiErrorResponse.crearResponseConListaDeErrores(requestURI, errors));
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = BAD_REQUEST)
    public ApiErrorResponse handleBusinessException(BusinessException ex, ServletWebRequest request) {
        return ApiErrorResponse.crearResponseConError(request.getRequest().getRequestURI(), ex.getApiError());
    }

    private String getGenericErrorMessageAndCause(Exception ex) {
        if (ex.getCause() != null) {
            return ex.getCause().toString() + ex.getMessage();
        }
        return "Exception: " + ex.getMessage();
    }
}
