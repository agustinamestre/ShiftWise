package com.agustinamestre.ShiftWiseBackend.exceptions;

import com.agustinamestre.ShiftWiseBackend.models.error.ApiError;

public class BusinessException extends BaseRunTimeException {
    public BusinessException(ApiError apiError) {
        super(apiError);
    }

}
