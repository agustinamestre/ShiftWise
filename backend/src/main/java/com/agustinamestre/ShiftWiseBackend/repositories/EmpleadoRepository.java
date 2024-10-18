package com.agustinamestre.ShiftWiseBackend.repositories;

import com.agustinamestre.ShiftWiseBackend.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {

   Optional<Empleado> findByNroDocumento(String nroDocumento);
}
