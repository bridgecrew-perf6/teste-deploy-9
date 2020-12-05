package com.clinic.repository;

import com.clinic.domain.Clinica;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Clinica entity.
 */
@Repository
public interface ClinicaRepository extends JpaRepository<Clinica, Long> {

    @Query(value = "select distinct clinica from Clinica clinica left join fetch clinica.proprietarios",
        countQuery = "select count(distinct clinica) from Clinica clinica")
    Page<Clinica> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct clinica from Clinica clinica left join fetch clinica.proprietarios")
    List<Clinica> findAllWithEagerRelationships();

    @Query("select clinica from Clinica clinica left join fetch clinica.proprietarios where clinica.id =:id")
    Optional<Clinica> findOneWithEagerRelationships(@Param("id") Long id);
}
