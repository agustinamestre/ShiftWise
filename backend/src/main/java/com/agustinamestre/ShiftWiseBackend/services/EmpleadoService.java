package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.EmpleadoRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {

    EmpleadoDTO crearEmpleado(EmpleadoRequest request);
    List<EmpleadoDTO> obtenerEmpleados();
    EmpleadoDTO obtenerEmpleado(String id);
    EmpleadoDTO editarEmpleado(String id, EmpleadoRequest request);
    void eliminarEmpleado(String id);
}
