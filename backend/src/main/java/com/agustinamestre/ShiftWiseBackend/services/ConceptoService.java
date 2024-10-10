package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.controllers.responses.ConceptoDTO;

import java.util.List;

public interface ConceptoService {
    List<ConceptoDTO> obtenerConceptos();

}
