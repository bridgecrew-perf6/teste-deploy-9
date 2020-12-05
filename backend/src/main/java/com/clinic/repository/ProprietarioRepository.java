package com.clinic.repository;

import com.clinic.domain.Proprietario;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Proprietario entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProprietarioRepository extends JpaRepository<Proprietario, Long> {
}
