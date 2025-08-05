package com.agustinamestre.ShiftWiseBackend.controllers.responses;


import com.agustinamestre.ShiftWiseBackend.domain.User;
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

    public static UserDTO mapFromUser(User user){
       return UserDTO.builder()
                .nroDocumento(user.getNroDocumento())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .email(user.getEmail())
                .fechaNacimiento(user.getFechaNacimiento())
                .fechaIngreso(user.getFechaIngreso())
                .foto(user.getFotoBase64())
                .build();
    }
}
