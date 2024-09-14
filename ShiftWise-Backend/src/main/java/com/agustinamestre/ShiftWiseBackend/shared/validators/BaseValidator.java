package com.agustinamestre.ShiftWiseBackend.shared.validators;

import jakarta.validation.ConstraintValidatorContext;

public abstract class BaseValidator {
    protected void changeDefaultMessage(ConstraintValidatorContext context, String message){
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
