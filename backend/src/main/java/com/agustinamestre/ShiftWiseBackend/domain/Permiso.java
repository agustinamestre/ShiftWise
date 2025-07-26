package com.agustinamestre.ShiftWiseBackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * Entidad que representa los permisos de acceso en el sistema.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPermiso;
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false, unique = true)
    private String nombre;
    @ManyToMany(mappedBy = "permisos", fetch = FetchType.LAZY)
    private Set<Perfil> perfiles;

    @Override
    public String toString() {
        return nombre;
    }
}
