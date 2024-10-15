package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.EmpleadoRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.EmpleadoDTO;
import com.agustinamestre.ShiftWiseBackend.services.EmpleadoService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/shiftwise/v1/empleados")
public class EmpleadoController {
    EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping(path="")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@RequestBody @Valid EmpleadoRequest request){
        return ResponseEntity.ok(empleadoService.crearEmpleado(request)) ;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<EmpleadoDTO> obtenerEmpleados() {
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoDTO obtenerEmpleado(@PathVariable String id) {
        return empleadoService.obtenerEmpleado(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmpleadoDTO editarEmpleado(@PathVariable String id, @Valid @RequestBody EmpleadoRequest request){
        return empleadoService.editarEmpleado(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarEmpleado(@PathVariable String id) {
        empleadoService.eliminarEmpleado(id);
    }
}