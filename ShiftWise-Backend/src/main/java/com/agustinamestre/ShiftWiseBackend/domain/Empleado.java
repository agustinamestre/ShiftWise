package com.agustinamestre.ShiftWiseBackend.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
@Entity(name = "empleado")
@AllArgsConstructor @NoArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String nroDocumento;
    @Column(nullable = false)
    String nombre;
    @Column(nullable = false)
    String apellido;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    LocalDate fechaNacimiento;
    @Column(nullable = false)
    LocalDate fechaIngreso;
    @CreatedDate
    LocalDate fechaCreacion = LocalDate.now();
    @Column(nullable = false)
    String foto;
    @Column(nullable = false)
    String password;
}
