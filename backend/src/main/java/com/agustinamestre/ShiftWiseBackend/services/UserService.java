package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserCreateRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserUpdateRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.UserDTO;
import com.agustinamestre.ShiftWiseBackend.domain.User;

import java.util.List;

public interface UserService {

    UserDTO crearUser(UserCreateRequest request);
    List<UserDTO> obtenerUsers();
    UserDTO obtenerUser(String id);
    UserDTO editarUser(String id, UserUpdateRequest request);
    void eliminarUser(String id);
    User obtenerUsuario(String documento, String password);
}
