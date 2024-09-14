package com.agustinamestre.ShiftWiseBackend.shared.validators.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

    String message() default "El email ingresado no es correcto.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
