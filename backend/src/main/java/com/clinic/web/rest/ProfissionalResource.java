package com.clinic.web.rest;

import com.clinic.domain.Profissional;
import com.clinic.repository.ProfissionalRepository;
import com.clinic.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.clinic.domain.Profissional}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProfissionalResource {

    private final Logger log = LoggerFactory.getLogger(ProfissionalResource.class);

    private static final String ENTITY_NAME = "profissional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProfissionalRepository profissionalRepository;

    public ProfissionalResource(ProfissionalRepository profissionalRepository) {
        this.profissionalRepository = profissionalRepository;
    }

    /**
     * {@code POST  /profissionals} : Create a new profissional.
     *
     * @param profissional the profissional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new profissional, or with status {@code 400 (Bad Request)} if the profissional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/profissionals")
    public ResponseEntity<Profissional> createProfissional(@RequestBody Profissional profissional) throws URISyntaxException {
        log.debug("REST request to save Profissional : {}", profissional);
        if (profissional.getId() != null) {
            throw new BadRequestAlertException("A new profissional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Profissional result = profissionalRepository.save(profissional);
        return ResponseEntity.created(new URI("/api/profissionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /profissionals} : Updates an existing profissional.
     *
     * @param profissional the profissional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated profissional,
     * or with status {@code 400 (Bad Request)} if the profissional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the profissional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/profissionals")
    public ResponseEntity<Profissional> updateProfissional(@RequestBody Profissional profissional) throws URISyntaxException {
        log.debug("REST request to update Profissional : {}", profissional);
        if (profissional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Profissional result = profissionalRepository.save(profissional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, profissional.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /profissionals} : get all the profissionals.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of profissionals in body.
     */
    @GetMapping("/profissionals")
    public List<Profissional> getAllProfissionals() {
        log.debug("REST request to get all Profissionals");
        return profissionalRepository.findAll();
    }

    /**
     * {@code GET  /profissionals/:id} : get the "id" profissional.
     *
     * @param id the id of the profissional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the profissional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/profissionals/{id}")
    public ResponseEntity<Profissional> getProfissional(@PathVariable Long id) {
        log.debug("REST request to get Profissional : {}", id);
        Optional<Profissional> profissional = profissionalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(profissional);
    }

    /**
     * {@code DELETE  /profissionals/:id} : delete the "id" profissional.
     *
     * @param id the id of the profissional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/profissionals/{id}")
    public ResponseEntity<Void> deleteProfissional(@PathVariable Long id) {
        log.debug("REST request to delete Profissional : {}", id);
        profissionalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
