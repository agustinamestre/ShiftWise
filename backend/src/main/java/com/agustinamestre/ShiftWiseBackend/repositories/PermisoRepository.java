package com.agustinamestre.ShiftWiseBackend.repositories;

import com.agustinamestre.ShiftWiseBackend.domain.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository  extends JpaRepository<Permiso, Long> {
}
