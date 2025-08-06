package com.agustinamestre.ShiftWiseBackend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter @Builder
@Entity
@Table(name = "users")
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
    @Column(columnDefinition = "TEXT")
    String fotoBase64;

    @Column(nullable = false)
    String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Jornada> jornadas = new ArrayList<>();

}