package com.agustinamestre.ShiftWiseBackend.exceptions;

import com.agustinamestre.ShiftWiseBackend.models.error.ApiError;

public class ResourceNotFoundException extends BaseRunTimeException {
    public ResourceNotFoundException(ApiError apiError) {
        super(apiError);
    }
}
