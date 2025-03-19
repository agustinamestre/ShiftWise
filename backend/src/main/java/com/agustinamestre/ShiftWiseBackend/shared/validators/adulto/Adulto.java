package com.agustinamestre.ShiftWiseBackend.shared.validators.adulto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AdultoValidator.class)
public @interface Adulto {
    String message() default "La edad del usuario no puede ser menor a 18 años.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
