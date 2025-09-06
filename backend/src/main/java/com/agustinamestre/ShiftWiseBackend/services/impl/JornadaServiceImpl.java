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
import com.agustinamestre.ShiftWiseBackend.repositories.UserRepository;
import com.agustinamestre.ShiftWiseBackend.repositories.JornadaRepository;
import com.agustinamestre.ShiftWiseBackend.services.JornadaService;
import com.agustinamestre.ShiftWiseBackend.services.validator.ValidadorJornadasFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JornadaServiceImpl implements JornadaService {

    final UserRepository userRepository;
    final ConceptoRepository conceptoRepository;
    final JornadaRepository jornadaRepository;
    final ValidadorJornadasFactory validadorJornadasFactory;

    @Autowired
    public JornadaServiceImpl(
            UserRepository userRepository,
            ConceptoRepository conceptoRepository,
            JornadaRepository jornadaRepository,
            ValidadorJornadasFactory validadorJornadasFactory
    ) {
        this.userRepository = userRepository;
        this.conceptoRepository = conceptoRepository;
        this.jornadaRepository = jornadaRepository;
        this.validadorJornadasFactory = validadorJornadasFactory;
    }

    @Override
    public JornadaDTO crearJornada(JornadaRequest request) {

        var user = userRepository.findById(request.getIdUser())
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.USER_NOT_FOUND));

        var concepto = conceptoRepository.findById(request.getIdConcepto())
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.CONCEPTO_NOT_FOUND));

        var jornada = Jornada.mapFromJornadaRequest(request);

        jornada.agregarConcepto(concepto);
        jornada.setUser(user);

        validadorJornadasFactory.obtenerValidador(concepto.obtenerConceptoType()).validar(jornada);

        validarConceptosEnJornada(jornada, concepto);

        validarHorasTrabajadasMismoDia(jornada, concepto);

        jornadaRepository.save(jornada);

        return JornadaDTO.mapFromJornada(jornada, concepto);
    }

    @Override
    public List<JornadaDTO> obtenerJornadas(String nroDocumento, LocalDate fecha, String apellido) {

        return jornadaRepository.obtenerJornadas(nroDocumento, fecha, apellido)
                .stream()
                .flatMap(jornada -> jornada.getConceptos()
                        .stream()
                        .map(concept -> JornadaDTO.mapFromJornada(jornada, concept)))
                .toList();
    }

    private void validarConceptosEnJornada(Jornada jornada, Concepto concepto) {
        //jornadas de un usuario para el mismo dia
        var jornadas = jornadaRepository.jornadasUserMismoDia(jornada.getUser().getNroDocumento(), jornada.getFecha());

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

        var jornadas = jornadaRepository.jornadasUserMismoDia(jornada.getUser().getNroDocumento(), jornada.getFecha());

        int horasTrabajadasTotal = jornadas.stream()
                .mapToInt(Jornada::getHorasTrabajadas)
                .reduce(0, Integer::sum);

        int horasTrabajadasAAgregar = jornada.getHorasTrabajadas();

        if (horasTrabajadasTotal + horasTrabajadasAAgregar > 12) {
            throw new BusinessException(ShiftWiseErrors.NO_MAS_DE_12_HORAS_MISMO_DIA);
        }
    }
}