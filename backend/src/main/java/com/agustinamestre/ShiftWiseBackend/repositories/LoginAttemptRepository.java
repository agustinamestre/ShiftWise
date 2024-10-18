package com.agustinamestre.ShiftWiseBackend.repositories;
import com.agustinamestre.ShiftWiseBackend.domain.LoginAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long> {

    List<LoginAttempt> findTop10ByNroDocumentoOrderByCreatedAtDesc(String nroDocumento);
}
