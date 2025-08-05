package com.agustinamestre.ShiftWiseBackend.repositories;

import com.agustinamestre.ShiftWiseBackend.domain.NombrePerfil;
import com.agustinamestre.ShiftWiseBackend.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

    Optional<Perfil> findByNombre(NombrePerfil nombre);
}
