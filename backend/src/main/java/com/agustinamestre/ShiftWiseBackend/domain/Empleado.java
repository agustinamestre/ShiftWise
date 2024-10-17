package com.agustinamestre.ShiftWiseBackend.domain;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.EmpleadoRequest;
import com.github.ksuid.Ksuid;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter @Builder
@Entity(name = "empleados")
@AllArgsConstructor @NoArgsConstructor
public class Empleado {

    @Id
    String id;

    @Column(nullable = false, unique = true)
    String nroDocumento;

    @Column(nullable = false)
    String nombre;

    @Column(nullable = false)
    String apellido;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    LocalDate fechaNacimiento;

    @Column(nullable = false)
    LocalDate fechaIngreso;

    @CreatedDate
    LocalDate fechaCreacion = LocalDate.now();

    @Column(nullable = false)
    String fotoBase64;

    @Column(nullable = false)
    String password;

    @OneToMany(mappedBy="empleado", cascade = CascadeType.ALL)
    private List<Jornada> jornadas = new ArrayList<>();

    public static Empleado mapFromEmpleadoRequest(EmpleadoRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

        return Empleado.builder()
                .id("emp-" + Ksuid.newKsuid().toString())
                .nroDocumento(request.getNroDocumento())
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .email(request.getEmail())
                .fechaNacimiento(request.getFechaNacimiento())
                .fechaCreacion(request.getFechaCreacion())
                .fechaIngreso(request.getFechaIngreso())
                .fotoBase64(request.getFotoBase64())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }
}