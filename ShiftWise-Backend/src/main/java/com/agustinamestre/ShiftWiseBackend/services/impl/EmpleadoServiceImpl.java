package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.RequestEmpleado;
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
    public EmpleadoDTO crearEmpleado(RequestEmpleado request) {

        var empleado = modelMapper.map(request, Empleado.class);

        try {
            empleadoRepository.save(empleado);
        } catch (DataIntegrityViolationException e) {
            manejarDataIntegrityViolationException(e);
        }

        return EmpleadoDTO.mapFromEmpleado(empleado);
    }

    private void manejarDataIntegrityViolationException(DataIntegrityViolationException e) {
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
    public EmpleadoDTO editarEmpleado(String id, RequestEmpleado request) {
        var empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.EMPLEADO_NOT_FOUND));

        Empleado.builder()
                .nroDocumento(request.getNroDocumento())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .fechaNacimiento(request.getFechaNacimiento())
                .fechaIngreso(request.getFechaIngreso())
                .build();

        try {
            empleadoRepository.save(empleado);
        } catch (DataIntegrityViolationException e) {
            manejarDataIntegrityViolationException(e);
        }

        return EmpleadoDTO.mapFromEmpleado(empleado);
    }

    @Override
    public void eliminarEmpleado(String id) {
        var empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.EMPLEADO_NOT_FOUND));

        empleadoRepository.delete(empleado);
    }
}