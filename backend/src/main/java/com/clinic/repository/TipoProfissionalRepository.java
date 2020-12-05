package com.clinic.repository;

import com.clinic.domain.TipoProfissional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoProfissional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoProfissionalRepository extends JpaRepository<TipoProfissional, Long> {
}
