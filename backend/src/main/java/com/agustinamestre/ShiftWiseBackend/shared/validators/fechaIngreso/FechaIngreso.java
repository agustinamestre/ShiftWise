package com.agustinamestre.ShiftWiseBackend.shared.validators.fechaIngreso;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FechaIngresoValidator.class)
public @interface FechaIngreso {
    String message() default "La fecha de ingreso es requerida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
