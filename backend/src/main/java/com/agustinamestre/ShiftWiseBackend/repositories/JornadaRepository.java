package com.agustinamestre.ShiftWiseBackend.repositories;

import com.agustinamestre.ShiftWiseBackend.domain.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, String> {

    @Query("SELECT j FROM jornadas j WHERE j.empleado.nroDocumento = :nroDocumento AND j.fecha = :fecha")
    List<Jornada> jornadasEmpleadoMismoDia(@Param("nroDocumento") String nroDocumento, @Param("fecha") LocalDate fecha);

    @Query("SELECT j FROM jornadas j WHERE (:nroDocumento is NULL or j.employee.nroDocumento = :nroDocumento) and " + "(:fecha is NULL or j.fecha = :fecha)")
    List<Jornada> obtenerJornadas(@Param("nroDocumento") String nroDocumento, @Param("fecha") LocalDate fecha);
}
