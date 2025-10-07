package com.agustinamestre.ShiftWiseBackend.controllers;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserCreateRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserUpdateRequest;
import com.agustinamestre.ShiftWiseBackend.controllers.responses.UserDTO;
import com.agustinamestre.ShiftWiseBackend.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/shiftwise/v1/users")
public class UserController {
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path="")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserDTO> signIn(@RequestBody @Valid UserCreateRequest request){
        UserDTO userDto = userService.crearUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> obtenerUsers() {
        return userService.obtenerUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO obtenerUser(@PathVariable String id) {
        return userService.obtenerUser(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO editarUser(@PathVariable String id, @Valid @RequestBody UserUpdateRequest request){
        return userService.editarUser(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminarUser(@PathVariable String id) {
        userService.eliminarUser(id);
    }
}