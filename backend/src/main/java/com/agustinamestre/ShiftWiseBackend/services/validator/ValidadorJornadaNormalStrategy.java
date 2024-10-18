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
public class ValidadorJornadaNormalStrategy implements ValidadorJornada {

    @Override
    public void validar(Jornada jornada) {
        if (isNull(jornada.getHorasTrabajadas())) {
            throw new BusinessException(ShiftWiseErrors.HS_TRABAJADAS_REQUERIDAS);
        }

        if (jornada.getHorasTrabajadas() < 6 || jornada.getHorasTrabajadas() > 8) {
            throw new BusinessException(ShiftWiseErrors.HORAS_NORMAL);
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

        int horasTrabajadasTotal = jornadas.stream()
                .filter(j -> j.getHorasTrabajadas() != null)
                .mapToInt(Jornada::getHorasTrabajadas)
                .reduce(0, Integer::sum);

        int horasTrabajadasAAgregar = jornada.getHorasTrabajadas();

        if (horasTrabajadasTotal + horasTrabajadasAAgregar > 48) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_48_HORAS_POR_SEMANA);
        }


        //cuento la cantidad de turnos normales en la semana
        long turnosNormales = jornadas.stream()
                .map(Jornada::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.obtenerConceptoType() == ConceptoType.NORMAL)
                .count();

        if (turnosNormales == 5) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_5_TURNOS_NORMALES_POR_SEMANA);
        }

    }
}
