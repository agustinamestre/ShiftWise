package com.agustinamestre.ShiftWiseBackend.shared.validators.fechaIngreso;

import com.agustinamestre.ShiftWiseBackend.shared.validators.BaseValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.util.Objects.isNull;

public class FechaIngresoValidator extends BaseValidator implements ConstraintValidator<FechaIngreso, LocalDate> {
    @Override
    public boolean isValid(LocalDate fechaIngreso, ConstraintValidatorContext context) {

        if(isNull(fechaIngreso)){
            changeDefaultMessage(context, "La fecha de ingreso es requerida.");
            return false;
        }

        if (fechaIngreso.isAfter(LocalDate.now())) {
            changeDefaultMessage(context, "La fecha de ingreso no puede ser posterior al día de la fecha");
            return false;
        }

        return true;
    }
}
