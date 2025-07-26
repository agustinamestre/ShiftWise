package com.agustinamestre.ShiftWiseBackend.domain;

import com.agustinamestre.ShiftWiseBackend.controllers.requests.UserRequest;
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
@Entity(name = "users")
@AllArgsConstructor @NoArgsConstructor
public class User {

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

    @Lob
    String fotoBase64;

    @Column(nullable = false)
    String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Jornada> jornadas = new ArrayList<>();



    public static User mapFromUserRequest(UserRequest request) {
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
                .build();
    }
}