package com.agustinamestre.ShiftWiseBackend.shared.validators.texto;

import com.agustinamestre.ShiftWiseBackend.shared.validators.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isAlphaSpace;
import static org.apache.commons.lang3.StringUtils.isBlank;
public class TextoValidoValidator extends BaseValidator implements ConstraintValidator<TextoValido, String> {

    private String fieldName;

    @Override
    public void initialize(TextoValido constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String campo, ConstraintValidatorContext context) {

        if (isBlank(campo)) {
            changeDefaultMessage(context, String.format("El campo %s es requerido.", fieldName));
            return false;
        }

        if (!isAlphaSpace(campo)) {
            changeDefaultMessage(context, String.format("Campo %s invalido.", fieldName));
            return false;
        }

        return true;
    }
}
