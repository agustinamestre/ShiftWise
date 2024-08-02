package com.agustinamestre.ShiftWiseBackend.shared.validators.texto;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TextoValidoValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TextoValido {
    String message() default "El campo es inválido.";
    String fieldName() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
