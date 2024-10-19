package com.agustinamestre.ShiftWiseBackend.services.validator;

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
public class ValidadorJornadaLibreStrategy implements ValidadorJornada {

    @Override
    public void validar(Jornada jornada) {

        if (!isNull(jornada.getHorasTrabajadas())) {
            throw new BusinessException(ShiftWiseErrors.HS_TRABAJADAS_NO_REQUERIDAS);
        }

        LocalDate fecha = jornada.getFecha();
        LocalDate lunes = fecha.with(DayOfWeek.MONDAY); //busco el lunes de esa semana
        LocalDate domingo = fecha.with(DayOfWeek.SUNDAY); //busco el domingo de esa semana

        var jornadas = jornada.getUser()
                .getJornadas()
                .stream()
                .filter(j -> j.getFecha()
                        .isEqual(lunes) || j.getFecha()
                        .isEqual(domingo) || (j.getFecha()
                        .isAfter(lunes) && j.getFecha()
                        .isBefore(domingo)))
                .toList();

        //cuento la cantidad de dias libres en la semana
        long diasLibres = jornadas.stream()
                .map(Jornada::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.obtenerConceptoType() == ConceptoType.LIBRE)
                .count();

        if (diasLibres == 2) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_2_TURNOS_LIBRE_POR_SEMANA);
        }
    }
}
