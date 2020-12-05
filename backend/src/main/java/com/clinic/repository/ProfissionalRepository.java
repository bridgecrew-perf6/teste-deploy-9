package com.clinic.repository;

import com.clinic.domain.Profissional;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Profissional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {
}
