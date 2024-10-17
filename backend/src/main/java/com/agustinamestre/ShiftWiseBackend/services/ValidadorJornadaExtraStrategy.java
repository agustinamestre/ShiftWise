package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.domain.ConceptoType;
import com.agustinamestre.ShiftWiseBackend.domain.Jornada;
import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;

import static java.util.Objects.isNull;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Component
public class ValidadorJornadaExtraStrategy implements ValidadorJornada{

    @Override
    public void validar(Jornada jornada) {
        if (isNull(jornada.getHorasTrabajadas())) {
            throw new BusinessException(ShiftWiseErrors.HS_TRABAJADAS_REQUERIDAS);
        }

        if (jornada.getHorasTrabajadas() < 2 || jornada.getHorasTrabajadas() > 6) {
            throw new BusinessException(ShiftWiseErrors.HORAS_EXTRA);
        }

        LocalDate fecha = jornada.getFecha();
        LocalDate lunes = fecha.with(DayOfWeek.MONDAY); //busco el lunes de esa semana
        LocalDate domingo = fecha.with(DayOfWeek.SUNDAY); //busco el domingo de esa semana

        var jornadas = jornada.getEmpleado()
                .getJornadas()
                .stream()
                .filter(j -> j.getFecha()
                        .isEqual(lunes) || j.getFecha()
                        .isEqual(domingo) || (j.getFecha()
                        .isAfter(lunes) && j.getFecha()
                        .isBefore(domingo)))
                .toList();

        //cuento la cantidad de turnos extra en la semana
        long turnosExtra = jornadas.stream()
                .map(Jornada::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.obtenerConceptoType() == ConceptoType.EXTRA)
                .count();

        if (turnosExtra == 3) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_3_TURNOS_EXTRA_POR_SEMANA);
        }
    }
}
