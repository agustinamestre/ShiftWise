package com.agustinamestre.ShiftWiseBackend.controllers.responses;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class UserDTO implements Serializable {
    String nroDocumento;
    String nombre;
    String apellido;
    String email;
    LocalDate fechaNacimiento;
    LocalDate fechaIngreso;
    String foto;

}
