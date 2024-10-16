package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.EmpleadoRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.EmpleadoDTO;
import com.agustinamestre.ShiftWiseBackend.domain.Empleado;
import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.exceptions.ResourceNotFoundException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;
import com.agustinamestre.ShiftWiseBackend.repositories.EmpleadoRepository;
import com.agustinamestre.ShiftWiseBackend.services.EmpleadoService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.requireNonNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class EmpleadoServiceImpl implements EmpleadoService {
    EmpleadoRepository empleadoRepository;
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public EmpleadoDTO crearEmpleado(EmpleadoRequest request) {

        var empleado = Empleado.mapFromEmpleadoRequest(request);

        try {
            empleadoRepository.save(empleado);
        } catch (DataIntegrityViolationException e) {
            validarDuplicidadEmpleado(e);
        }

        return EmpleadoDTO.mapFromEmpleado(empleado);
    }

    private void validarDuplicidadEmpleado(DataIntegrityViolationException e) {
        String rootCauseMessage = requireNonNull(e.getRootCause()).getLocalizedMessage();

        if (rootCauseMessage.contains("nro_documento")) {
            throw new BusinessException(ShiftWiseErrors.DOCUMENTO_EXISTENTE);
        } else if (rootCauseMessage.contains("email")) {
            throw new BusinessException(ShiftWiseErrors.EMAIL_EXISTENTE);
        }
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleados() {
        return empleadoRepository.findAll().stream()
                .map(EmpleadoDTO::mapFromEmpleado)
                .toList();
    }

    @Override
    public EmpleadoDTO obtenerEmpleado(String id) {
        var empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.EMPLEADO_NOT_FOUND));

        return EmpleadoDTO.mapFromEmpleado(empleado);
    }

    @Override
    public EmpleadoDTO editarEmpleado(String id, EmpleadoRequest request) {
        var empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.EMPLEADO_NOT_FOUND));

        empleado.setNroDocumento(request.getNroDocumento());
        empleado.setNombre(request.getNombre());
        empleado.setApellido(request.getApellido());
        empleado.setEmail(request.getEmail());
        empleado.setFechaNacimiento(request.getFechaNacimiento());
        empleado.setFechaIngreso(request.getFechaIngreso());

        try {
            empleadoRepository.save(empleado);
        } catch (DataIntegrityViolationException e) {
            validarDuplicidadEmpleado(e);
        }

        return EmpleadoDTO.mapFromEmpleado(empleado);
    }

    @Override
    public void eliminarEmpleado(String id) {
        try {
            empleadoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ShiftWiseErrors.EMPLEADO_NOT_FOUND);
        }
    }
}