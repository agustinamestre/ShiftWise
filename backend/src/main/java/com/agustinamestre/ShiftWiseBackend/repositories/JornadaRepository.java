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

    @Query("SELECT j FROM jornadas j WHERE j.user.nroDocumento = :nroDocumento AND j.fecha = :fecha")
    List<Jornada> jornadasUserMismoDia(@Param("nroDocumento") String nroDocumento, @Param("fecha") LocalDate fecha);

    @Query("SELECT j FROM jornadas j " +
            "WHERE (:nroDocumento IS NULL OR j.user.nroDocumento = :nroDocumento) " +
            "AND (:fecha IS NULL OR j.fecha = :fecha)" +
            "AND (:apellido IS NULL OR LOWER(j.user.apellido) LIKE LOWER(CONCAT('%', :apellido, '%')))")
    List<Jornada> obtenerJornadas(@Param("nroDocumento") String nroDocumento,
                                  @Param("fecha") LocalDate fecha,
                                  @Param("apellido") String apellido);
}
