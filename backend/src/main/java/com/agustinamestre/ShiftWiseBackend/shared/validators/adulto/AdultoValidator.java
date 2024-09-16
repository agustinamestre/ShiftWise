package com.agustinamestre.ShiftWiseBackend.shared.validators.adulto;

import com.agustinamestre.ShiftWiseBackend.shared.validators.BaseValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

import static java.util.Objects.isNull;

public class AdultoValidator extends BaseValidator implements ConstraintValidator<Adulto, LocalDate> {

    @Override
    public boolean isValid(LocalDate fechaNacimiento, ConstraintValidatorContext context) {
        if (isNull(fechaNacimiento)) {
            changeDefaultMessage(context, "La fecha de nacimiento es requerida.");
            return false;
        }

        if (fechaNacimiento.isAfter(LocalDate.now())) {
            changeDefaultMessage(context, "La fecha de nacimiento no puede ser posterior al día de la fecha");
            return false;
        }

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        return edad >= 18;
    }
}
