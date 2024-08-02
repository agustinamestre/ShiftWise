package com.agustinamestre.ShiftWiseBackend.controllers.requests;

import com.agustinamestre.ShiftWiseBackend.shared.validators.adulto.Adulto;
import com.agustinamestre.ShiftWiseBackend.shared.validators.email.Email;
import com.agustinamestre.ShiftWiseBackend.shared.validators.fechaIngreso.FechaIngreso;
import com.agustinamestre.ShiftWiseBackend.shared.validators.texto.TextoValido;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
public class RequestEmpleado implements Serializable {

    @NotBlank(message = "El numero de documento es requerido.")
    String nroDocumento;

    @TextoValido(fieldName = "nombre")
    String nombre;

    @TextoValido(fieldName = "apellido")
    String apellido;

    @Email
    String email;

    @Adulto
    LocalDate fechaNacimiento;

    @FechaIngreso
    LocalDate fechaIngreso;

    LocalDate fechaCreacion = LocalDate.now();

    String foto;

    @NotBlank(message = "La contraseña es requerida.")
    String password;
}