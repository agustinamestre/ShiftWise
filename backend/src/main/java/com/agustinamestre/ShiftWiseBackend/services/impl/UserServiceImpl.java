package com.agustinamestre.ShiftWiseBackend.services.impl;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.UserDTO;
import com.agustinamestre.ShiftWiseBackend.domain.NombrePerfil;
import com.agustinamestre.ShiftWiseBackend.domain.Perfil;
import com.agustinamestre.ShiftWiseBackend.domain.User;
import com.agustinamestre.ShiftWiseBackend.exceptions.BusinessException;
import com.agustinamestre.ShiftWiseBackend.exceptions.ResourceNotFoundException;
import com.agustinamestre.ShiftWiseBackend.exceptions.UnauthorizedException;
import com.agustinamestre.ShiftWiseBackend.models.error.ShiftWiseErrors;
import com.agustinamestre.ShiftWiseBackend.repositories.PerfilRepository;
import com.agustinamestre.ShiftWiseBackend.repositories.UserRepository;
import com.agustinamestre.ShiftWiseBackend.services.UserService;
import com.agustinamestre.ShiftWiseBackend.shared.mappers.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Transactional
public class UserServiceImpl implements UserService {


    UserRepository userRepository;
    PerfilRepository perfilRepository;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PerfilRepository perfilRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO crearUser(UserRequest request) {

        Perfil perfilEmpleado = perfilRepository.findByNombre(NombrePerfil.EMPLEADO)
                .orElseThrow(() -> new BusinessException(ShiftWiseErrors.PERFIL_NOT_FOUND));

        User user = UserMapper.mapFromUserRequest(request, perfilEmpleado);

        validarDuplicidadUser(request.getEmail(), request.getNroDocumento());

        userRepository.save(user);

        return UserMapper.mapFromUser(user);
    }

    @Override
    public UserDTO editarUser(String id, UserRequest request) {

        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.USER_NOT_FOUND));

        validarDuplicidadUser(request.getEmail(), request.getNroDocumento());

        UserMapper.updateEntityFromRequest(user, request);

        userRepository.save(user);

        return UserMapper.mapFromUser(user);
    }

    @Override
    public List<UserDTO> obtenerUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapFromUser)
                .toList();
    }

    @Override
    public UserDTO obtenerUser(String id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ShiftWiseErrors.USER_NOT_FOUND));

        return UserMapper.mapFromUser(user);
    }

    @Override
    public void eliminarUser(String id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(ShiftWiseErrors.USER_NOT_FOUND);
        }
    }

    public User obtenerUsuario(String documento, String password) {
        var usuario = userRepository.findByNroDocumento(documento)
                .orElseThrow(() -> new UnauthorizedException("Usuario inexistente"));

        if(!passwordEncoder.matches(password, usuario.getPassword())){
            throw new UnauthorizedException("Credenciales incorrectas");
        }

        return usuario;
    }

    private void validarDuplicidadUser(String email, String nroDocumento) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BusinessException(ShiftWiseErrors.EMAIL_EXISTENTE);
        }

        if (userRepository.findByNroDocumento(nroDocumento).isPresent()) {
            throw new BusinessException(ShiftWiseErrors.DOCUMENTO_EXISTENTE);
        }
    }
}