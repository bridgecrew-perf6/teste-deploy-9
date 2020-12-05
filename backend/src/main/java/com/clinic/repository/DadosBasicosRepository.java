package com.clinic.repository;

import com.clinic.domain.DadosBasicos;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DadosBasicos entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DadosBasicosRepository extends JpaRepository<DadosBasicos, Long> {
}
