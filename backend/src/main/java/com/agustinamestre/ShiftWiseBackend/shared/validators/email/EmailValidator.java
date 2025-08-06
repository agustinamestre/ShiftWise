package com.agustinamestre.ShiftWiseBackend.shared.validators.email;

import com.agustinamestre.ShiftWiseBackend.shared.validators.BaseValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static org.apache.commons.lang3.StringUtils.isBlank;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class EmailValidator extends BaseValidator implements ConstraintValidator<Email, String> {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        if(isBlank(email)){
            changeDefaultMessage(context, "El email es requerido.");
            return false;
        }

        Matcher matcher = PATTERN.matcher(email);
        return matcher.matches();
    }
}
