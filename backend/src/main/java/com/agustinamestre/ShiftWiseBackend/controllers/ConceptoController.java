package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.controllers.responses.ConceptoDTO;
import com.agustinamestre.ShiftWiseBackend.services.ConceptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shiftwise/v1/conceptos")
public class ConceptoController {

    ConceptoService conceptoService;
    @Autowired
    public ConceptoController(ConceptoService conceptoService) {
        this.conceptoService = conceptoService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<ConceptoDTO> obtenerConceptos() {
        return conceptoService.obtenerConceptos();
    }
}