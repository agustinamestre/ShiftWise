package com.agustinamestre.ShiftWiseBackend.exceptions;

import com.agustinamestre.ShiftWiseBackend.models.error.ApiError;
import lombok.Getter;

@Getter
public class BaseRunTimeException extends RuntimeException{
    private final ApiError apiError;

    public BaseRunTimeException(ApiError apiError) {
        super(apiError.getErrorMessage());
        this.apiError = apiError;
    }
}
