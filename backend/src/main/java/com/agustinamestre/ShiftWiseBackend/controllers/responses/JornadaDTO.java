package com.agustinamestre.ShiftWiseBackend.controllers.responses;

import com.agustinamestre.ShiftWiseBackend.domain.Concepto;
import com.agustinamestre.ShiftWiseBackend.domain.Jornada;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;


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
                .nroDocumento(jornada.getUser().getNroDocumento())
                .nombreCompleto(jornada.getUser().getNombre() + " " + jornada.getUser().getApellido())
                .fecha(jornada.getFecha())
                .concepto(concepto.getNombre())
                .horasTrabajadas(jornada.getHorasTrabajadas())
                .build();
    }
}
