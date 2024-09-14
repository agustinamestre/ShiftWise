package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.RequestEmpleado;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {

    EmpleadoDTO crearEmpleado(RequestEmpleado request);
    List<EmpleadoDTO> obtenerEmpleados();
    EmpleadoDTO obtenerEmpleado(String id);
    EmpleadoDTO editarEmpleado(String id, RequestEmpleado request);
    void eliminarEmpleado(String id);
}
