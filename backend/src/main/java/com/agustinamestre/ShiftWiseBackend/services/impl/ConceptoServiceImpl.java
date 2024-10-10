package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.controllers.responses.ConceptoDTO;
import com.agustinamestre.ShiftWiseBackend.domain.Concepto;
import com.agustinamestre.ShiftWiseBackend.repositories.ConceptoRepository;
import com.agustinamestre.ShiftWiseBackend.services.ConceptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConceptoServiceImpl implements ConceptoService {

    ConceptoRepository conceptoRepository;
    @Autowired
    public ConceptoServiceImpl(ConceptoRepository conceptoRepository) {
        this.conceptoRepository = conceptoRepository;
    }

    @Override
    public List<ConceptoDTO> obtenerConceptos() {
        var conceptos = conceptoRepository.findAll();
        List<ConceptoDTO> conceptoReponse = new ArrayList<>();

        for(Concepto concepto : conceptos){
            conceptoReponse.add(ConceptoDTO.mapFromConcepto(concepto));
        }

        return conceptoReponse;
    }
}
