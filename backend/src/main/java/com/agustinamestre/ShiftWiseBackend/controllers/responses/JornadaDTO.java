package com.agustinamestre.ShiftWiseBackend.controllers.responses;

import com.agustinamestre.ShiftWiseBackend.domain.Concepto;
import com.agustinamestre.ShiftWiseBackend.domain.Jornada;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class JornadaDTO implements Serializable {
    Integer idJornada;
    String nroDocumento;
    String nombreCompleto;
    LocalDate fecha;
    String concepto;
    Integer horasTrabajadas;

    public static JornadaDTO mapFromJornada(Jornada jornada, Concepto concepto){
        return JornadaDTO.builder()
                .idJornada(jornada.getIdJornada())
                .nroDocumento(jornada.getEmpleado().getNroDocumento())
                .nombreCompleto(jornada.getEmpleado().getNombre() + " " + jornada.getEmpleado().getApellido())
                .fecha(jornada.getFecha())
                .concepto(concepto.getNombre())
                .horasTrabajadas(jornada.getHorasTrabajadas())
                .build();
    }
}
