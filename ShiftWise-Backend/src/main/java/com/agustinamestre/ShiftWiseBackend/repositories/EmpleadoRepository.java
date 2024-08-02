package com.agustinamestre.ShiftWiseBackend.repositories;

import com.agustinamestre.ShiftWiseBackend.domain.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM empleado e WHERE e.nroDocumento = ?1")
    boolean existByDocumentNumber(String nroDocumento);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM empleado e WHERE e.email = ?1")
    boolean existByEmail(String email);
}
