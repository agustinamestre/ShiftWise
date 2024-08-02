package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.RequestEmpleado;
import com.agustinamestre.ShiftWiseBackend.domain.Empleado;
import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;
import com.agustinamestre.ShiftWiseBackend.repositories.EmpleadoRepository;
import com.agustinamestre.ShiftWiseBackend.services.EmpleadoService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void crearEmpleado(RequestEmpleado request) {

        var empleado = modelMapper.map(request, Empleado.class);

        if(empleadoRepository.existByDocumentNumber(empleado.getNroDocumento())){
            throw new BusinessException(ShiftWiseErrors.DOCUMENTO_EXISTENTE);
        }

        if(empleadoRepository.existByEmail(empleado.getEmail())){
            throw new BusinessException(ShiftWiseErrors.EMAIL_EXISTENTE);
        }

        empleadoRepository.save(empleado);
    }
}
