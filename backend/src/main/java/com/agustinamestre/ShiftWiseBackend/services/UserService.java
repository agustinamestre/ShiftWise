package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.UserDTO;
import com.agustinamestre.ShiftWiseBackend.domain.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO crearUser(UserRequest request);
    List<UserDTO> obtenerUsers();
    UserDTO obtenerUser(String id);
    UserDTO editarUser(String id, UserRequest request);
    void eliminarUser(String id);
    User obtenerUsuario(String documento, String password);
}
