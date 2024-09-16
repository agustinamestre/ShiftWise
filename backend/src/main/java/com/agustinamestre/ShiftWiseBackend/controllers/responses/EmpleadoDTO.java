package com.agustinamestre.ShiftWiseBackend.controllers.responses;


import com.agustinamestre.ShiftWiseBackend.domain.Empleado;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class EmpleadoDTO implements Serializable {
    String nroDocumento;
    String nombre;
    String apellido;
    String email;
    LocalDate fechaNacimiento;
    LocalDate fechaIngreso;
    String foto;

    public static EmpleadoDTO mapFromEmpleado(Empleado empleado){
       return EmpleadoDTO.builder()
                .nroDocumento(empleado.getNroDocumento())
                .nombre(empleado.getNombre())
                .apellido(empleado.getApellido())
                .email(empleado.getEmail())
                .fechaNacimiento(empleado.getFechaNacimiento())
                .fechaIngreso(empleado.getFechaIngreso())
                .foto(empleado.getFoto())
                .build();
    }
}
