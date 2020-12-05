package com.clinic.web.rest;

import com.clinic.domain.Clinica;
import com.clinic.repository.ClinicaRepository;
import com.clinic.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.clinic.domain.Clinica}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ClinicaResource {

    private final Logger log = LoggerFactory.getLogger(ClinicaResource.class);

    private static final String ENTITY_NAME = "clinica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ClinicaRepository clinicaRepository;

    public ClinicaResource(ClinicaRepository clinicaRepository) {
        this.clinicaRepository = clinicaRepository;
    }

    /**
     * {@code POST  /clinicas} : Create a new clinica.
     *
     * @param clinica the clinica to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new clinica, or with status {@code 400 (Bad Request)} if the clinica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/clinicas")
    public ResponseEntity<Clinica> createClinica(@Valid @RequestBody Clinica clinica) throws URISyntaxException {
        log.debug("REST request to save Clinica : {}", clinica);
        if (clinica.getId() != null) {
            throw new BadRequestAlertException("A new clinica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Clinica result = clinicaRepository.save(clinica);
        return ResponseEntity.created(new URI("/api/clinicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /clinicas} : Updates an existing clinica.
     *
     * @param clinica the clinica to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated clinica,
     * or with status {@code 400 (Bad Request)} if the clinica is not valid,
     * or with status {@code 500 (Internal Server Error)} if the clinica couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/clinicas")
    public ResponseEntity<Clinica> updateClinica(@Valid @RequestBody Clinica clinica) throws URISyntaxException {
        log.debug("REST request to update Clinica : {}", clinica);
        if (clinica.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Clinica result = clinicaRepository.save(clinica);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, clinica.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /clinicas} : get all the clinicas.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of clinicas in body.
     */
    @GetMapping("/clinicas")
    public List<Clinica> getAllClinicas(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Clinicas");
        return clinicaRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /clinicas/:id} : get the "id" clinica.
     *
     * @param id the id of the clinica to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the clinica, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/clinicas/{id}")
    public ResponseEntity<Clinica> getClinica(@PathVariable Long id) {
        log.debug("REST request to get Clinica : {}", id);
        Optional<Clinica> clinica = clinicaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(clinica);
    }

    /**
     * {@code DELETE  /clinicas/:id} : delete the "id" clinica.
     *
     * @param id the id of the clinica to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/clinicas/{id}")
    public ResponseEntity<Void> deleteClinica(@PathVariable Long id) {
        log.debug("REST request to delete Clinica : {}", id);
        clinicaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
