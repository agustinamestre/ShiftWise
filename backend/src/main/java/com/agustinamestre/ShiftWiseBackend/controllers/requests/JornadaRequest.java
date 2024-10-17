package com.agustinamestre.ShiftWiseBackend.controllers.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
public class JornadaRequest {

    @NotBlank(message = "El id del empleado es obligatorio.")
    String idEmpleado;

    @NotNull(message = "El id del concepto es obligatorio.")
    Integer idConcepto;

    @NotNull(message = "La fecha es obligatoria.")
    LocalDate fecha;

    Integer horasTrabajadas;
}
