package com.agustinamestre.ShiftWiseBackend.shared.mappers;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.UserDTO;
import com.agustinamestre.ShiftWiseBackend.domain.Perfil;
import com.agustinamestre.ShiftWiseBackend.domain.User;
import com.github.ksuid.Ksuid;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {

    public static void updateEntityFromRequest(User user, UserRequest request) {
        user.setNroDocumento(request.getNroDocumento());
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setFechaNacimiento(request.getFechaNacimiento());
        user.setFechaIngreso(request.getFechaIngreso());
        user.setFotoBase64(request.getFotoBase64());
    }

    public static UserDTO mapFromUser(User user){
        return UserDTO.builder()
                .nroDocumento(user.getNroDocumento())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .email(user.getEmail())
                .fechaNacimiento(user.getFechaNacimiento())
                .fechaIngreso(user.getFechaIngreso())
                .foto(user.getFotoBase64())
                .build();
    }

    public static User mapFromUserRequest(UserRequest request, Perfil perfil) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

        return User.builder()
                .id("usr-" + Ksuid.newKsuid().toString())
                .nroDocumento(request.getNroDocumento())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .fechaNacimiento(request.getFechaNacimiento())
                .fechaCreacion(request.getFechaCreacion())
                .fechaIngreso(request.getFechaIngreso())
                .fotoBase64(request.getFotoBase64())
                .password(passwordEncoder.encode(request.getPassword()))
                .perfil(perfil)
                .build();
    }


}
