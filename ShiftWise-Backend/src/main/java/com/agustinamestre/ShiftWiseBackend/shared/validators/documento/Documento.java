package com.agustinamestre.ShiftWiseBackend.shared.validators.documento;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DocumentoValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Documento {

    String message() default "Numero de documento invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
