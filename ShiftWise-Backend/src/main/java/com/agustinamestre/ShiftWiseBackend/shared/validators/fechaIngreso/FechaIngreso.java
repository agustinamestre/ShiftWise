package com.agustinamestre.ShiftWiseBackend.shared.validators.fechaIngreso;

import jakarta.validation.Payload;

public @interface FechaIngreso {
    String message() default "La fecha de ingreso es requerida.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
