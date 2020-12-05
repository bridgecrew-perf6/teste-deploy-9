package com.clinic.repository;

import com.clinic.domain.Procedimento;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Procedimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
}
