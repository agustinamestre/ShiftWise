package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.JornadaRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.JornadaDTO;

import java.time.LocalDate;
import java.util.List;

public interface JornadaService {

    JornadaDTO crearJornada(JornadaRequest request);

    List<JornadaDTO> obtenerJornadas(String nroDocumento, LocalDate fecha, String apellido);
}
