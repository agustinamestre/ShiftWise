package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.JornadaRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.JornadaDTO;
import com.agustinamestre.ShiftWiseBackend.services.JornadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shiftwise/v1/jornadas")
public class JornadaController {
    final JornadaService jornadaService;

    @Autowired
    public JornadaController(JornadaService jornadaService) {
        this.jornadaService = jornadaService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public JornadaDTO crearJornada(@Valid @RequestBody JornadaRequest request){
        return jornadaService.crearJornada(request);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<JornadaDTO> obtenerJornadas(
            @RequestParam(required = false) String nroDocumento,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) String apellido){
        return jornadaService.obtenerJornadas(nroDocumento, fecha, apellido);
    }
}
