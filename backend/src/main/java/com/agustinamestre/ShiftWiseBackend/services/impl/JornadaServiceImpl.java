package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.JornadaRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.JornadaDTO;
import com.agustinamestre.ShiftWiseBackend.domain.Concepto;
import com.agustinamestre.ShiftWiseBackend.domain.ConceptoType;
import com.agustinamestre.ShiftWiseBackend.domain.Jornada;
import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.exceptions.ResourceNotFoundException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;
import com.agustinamestre.ShiftWiseBackend.repositories.ConceptoRepository;
import com.agustinamestre.ShiftWiseBackend.repositories.EmpleadoRepository;
import com.agustinamestre.ShiftWiseBackend.repositories.JornadaRepository;
import com.agustinamestre.ShiftWiseBackend.services.JornadaService;
import com.agustinamestre.ShiftWiseBackend.services.ValidadorJornadasFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class JornadaServiceImpl implements JornadaService {

    final EmpleadoRepository empleadoRepository;
    final ConceptoRepository conceptoRepository;
    final JornadaRepository jornadaRepository;
    final ValidadorJornadasFactory validadorJornadasFactory;

    @Autowired
    public JornadaServiceImpl(
            EmpleadoRepository empleadoRepository,
            ConceptoRepository conceptoRepository,
            JornadaRepository jornadaRepository,
            ValidadorJornadasFactory validadorJornadasFactory
    ) {
        this.empleadoRepository = empleadoRepository;
        this.conceptoRepository = conceptoRepository;
        this.jornadaRepository = jornadaRepository;
        this.validadorJornadasFactory = validadorJornadasFactory;
    }

    @Override
    public JornadaDTO crearJornada(JornadaRequest request) {

        var empleado = empleadoRepository.findById(request.getIdEmpleado().toString())
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.EMPLEADO_NOT_FOUND));

        var concepto = conceptoRepository.findById(request.getIdConcepto())
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.CONCEPTO_NOT_FOUND));

        validadorJornadasFactory.obtenerValidador(concepto.obtenerConceptoType()).validar(request.getHorasTrabajadas());

        var jornada = Jornada.mapFromJornadaRequest(request);

        jornada.agregarConcepto(concepto);
        jornada.setEmpleado(empleado);

        validarJornada(jornada, concepto);

        validarHorasTrabajadasMismoDia(jornada, concepto);

        validarHorasSemanales(jornada, concepto);

        jornadaRepository.save(jornada);

        return JornadaDTO.mapFromJornada(jornada, concepto);
    }

    @Override
    public List<JornadaDTO> obtenerJornadas(String nroDocumento, LocalDate fecha) {

        return jornadaRepository.obtenerJornadas(nroDocumento, fecha)
                .stream()
                .flatMap(jornada -> jornada.getConceptos()
                        .stream()
                        .map(concept -> JornadaDTO.mapFromJornada(jornada, concept)))
                .toList();
    }

    private void validarJornada(Jornada jornada, Concepto concepto) {
        //jornadas de un empleado para el mismo dia
        var jornadas = jornadaRepository.jornadasEmpleadoMismoDia(jornada.getEmpleado().getNroDocumento(), jornada.getFecha());

        //conceptos de la jornada
        jornadas.stream()
                .map(Jornada::getConceptos)
                .flatMap(List::stream)
                .forEach(c -> {
                    if (concepto.obtenerConceptoType() == ConceptoType.LIBRE) {
                        //evitar dia libre en jornada con turno reservado
                        throw new BusinessException(ShiftWiseErrors.NO_DIA_LIBRE_EN_DIA_NORMAL);
                    }

                    if (concepto.obtenerConceptoType() == c.obtenerConceptoType()) {
                        //evitar dos conceptos iguales en la misma jornada
                        throw new BusinessException(ShiftWiseErrors.JORNADA_CONCEPTO_REPETIDO);
                    }

                    if (c.obtenerConceptoType() == ConceptoType.LIBRE) {
                        //evitar agregar turno a jornada con dia libre
                        throw new BusinessException(ShiftWiseErrors.JORNADA_LIBRE);
                    }
                });
    }

    private void validarHorasTrabajadasMismoDia(Jornada jornada, Concepto concepto){
        if (concepto.obtenerConceptoType() == ConceptoType.LIBRE) {
            return;
        }

        var jornadas = jornadaRepository.jornadasEmpleadoMismoDia(jornada.getEmpleado().getNroDocumento(), jornada.getFecha());

        int horasTrabajadasTotal = jornadas.stream()
                .mapToInt(Jornada::getHorasTrabajadas)
                .reduce(0, Integer::sum);

        int horasTrabajadasAAgregar = jornada.getHorasTrabajadas();

        if (horasTrabajadasTotal + horasTrabajadasAAgregar > 12) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_12_HORAS_MISMO_DIA);
        }
    }

    private void validarHorasSemanales(Jornada jornada, Concepto concepto){
        LocalDate fecha = jornada.getFecha();
        LocalDate lunes = fecha.with(DayOfWeek.MONDAY); //busco el lunes de esa semana
        LocalDate domingo = fecha.with(DayOfWeek.SUNDAY); //busco el domingo de esa semana

        if (concepto.obtenerConceptoType() == ConceptoType.LIBRE) {
            return;
        }

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

        //cuento la cantidad de turnos extra en la semana
        long turnosExtra = jornadas.stream()
                .map(Jornada::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.obtenerConceptoType() == ConceptoType.EXTRA)
                .count();

        if (turnosExtra == 3 && concepto.obtenerConceptoType() == ConceptoType.EXTRA) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_3_TURNOS_EXTRA_POR_SEMANA);
        }

        //cuento la cantidad de turnos normales en la semana
        long turnosNormales = jornadas.stream()
                .map(Jornada::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.obtenerConceptoType() == ConceptoType.NORMAL)
                .count();

        if (turnosNormales == 5 && concepto.obtenerConceptoType() == ConceptoType.NORMAL) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_5_TURNOS_NORMALES_POR_SEMANA);
        }

        //cuento la cantidad de dias libres en la semana
        long diasLibres = jornadas.stream()
                .map(Jornada::getConceptos)
                .flatMap(List::stream)
                .filter(c -> c.obtenerConceptoType() == ConceptoType.LIBRE)
                .count();

        if (diasLibres == 2 && concepto.obtenerConceptoType() == ConceptoType.LIBRE) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_2_TURNOS_LIBRE_POR_SEMANA);
        }
    }
}
