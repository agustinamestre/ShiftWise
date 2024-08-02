package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.RequestEmpleado;
import com.agustinamestre.ShiftWiseBackend.services.EmpleadoService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/shiftwise/v1")
public class EmpleadoController {
    EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping(path="/empleado")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> crearEmpleado(@RequestBody @Valid RequestEmpleado request){
        empleadoService.crearEmpleado(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
