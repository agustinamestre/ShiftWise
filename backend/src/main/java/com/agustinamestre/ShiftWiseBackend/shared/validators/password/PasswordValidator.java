package com.agustinamestre.ShiftWiseBackend.shared.validators.password;

import com.agustinamestre.ShiftWiseBackend.shared.validators.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;

public class PasswordValidator extends BaseValidator implements ConstraintValidator<Password, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if(isBlank(password)){
            changeDefaultMessage(context, "La contraseña es requerida.");
            return false;
        }

        return password.matches("^(?=.*[A-Z])(?=.*\\d).{8,}$");
    }
}
