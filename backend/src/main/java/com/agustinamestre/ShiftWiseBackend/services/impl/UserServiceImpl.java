package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.UserDTO;
import com.agustinamestre.ShiftWiseBackend.domain.User;
import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.exceptions.ResourceNotFoundException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;
import com.agustinamestre.ShiftWiseBackend.repositories.UserRepository;
import com.agustinamestre.ShiftWiseBackend.services.UserService;
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
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO crearUser(UserRequest request) {

        var user = User.mapFromUserRequest(request);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            validarDuplicidadUser(e);
        }

        return UserDTO.mapFromUser(user);
    }

    private void validarDuplicidadUser(DataIntegrityViolationException e) {
        String rootCauseMessage = requireNonNull(e.getRootCause()).getLocalizedMessage();

        if (rootCauseMessage.contains("nro_documento")) {
            throw new BusinessException(ShiftWiseErrors.DOCUMENTO_EXISTENTE);
        } else if (rootCauseMessage.contains("email")) {
            throw new BusinessException(ShiftWiseErrors.EMAIL_EXISTENTE);
        }
    }

    @Override
    public List<UserDTO> obtenerUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::mapFromUser)
                .toList();
    }

    @Override
    public UserDTO obtenerUser(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.USER_NOT_FOUND));

        return UserDTO.mapFromUser(user);
    }

    @Override
    public UserDTO editarUser(String id, UserRequest request) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.USER_NOT_FOUND));

        user.setNroDocumento(request.getNroDocumento());
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setFechaNacimiento(request.getFechaNacimiento());
        user.setFechaIngreso(request.getFechaIngreso());

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            validarDuplicidadUser(e);
        }

        return UserDTO.mapFromUser(user);
    }

    @Override
    public void eliminarUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ShiftWiseErrors.USER_NOT_FOUND);
        }
    }
}