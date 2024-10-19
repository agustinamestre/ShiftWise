package com.agustinamestre.ShiftWiseBackend.services;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO crearUser(UserRequest request);
    List<UserDTO> obtenerUsers();
    UserDTO obtenerUser(String id);
    UserDTO editarUser(String id, UserRequest request);
    void eliminarUser(String id);
}
