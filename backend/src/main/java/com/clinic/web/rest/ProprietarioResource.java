package com.clinic.web.rest;

import com.clinic.domain.Proprietario;
import com.clinic.repository.ProprietarioRepository;
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
 * REST controller for managing {@link com.clinic.domain.Proprietario}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProprietarioResource {

    private final Logger log = LoggerFactory.getLogger(ProprietarioResource.class);

    private static final String ENTITY_NAME = "proprietario";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProprietarioRepository proprietarioRepository;

    public ProprietarioResource(ProprietarioRepository proprietarioRepository) {
        this.proprietarioRepository = proprietarioRepository;
    }

    /**
     * {@code POST  /proprietarios} : Create a new proprietario.
     *
     * @param proprietario the proprietario to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new proprietario, or with status {@code 400 (Bad Request)} if the proprietario has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proprietarios")
    public ResponseEntity<Proprietario> createProprietario(@RequestBody Proprietario proprietario) throws URISyntaxException {
        log.debug("REST request to save Proprietario : {}", proprietario);
        if (proprietario.getId() != null) {
            throw new BadRequestAlertException("A new proprietario cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Proprietario result = proprietarioRepository.save(proprietario);
        return ResponseEntity.created(new URI("/api/proprietarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proprietarios} : Updates an existing proprietario.
     *
     * @param proprietario the proprietario to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated proprietario,
     * or with status {@code 400 (Bad Request)} if the proprietario is not valid,
     * or with status {@code 500 (Internal Server Error)} if the proprietario couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proprietarios")
    public ResponseEntity<Proprietario> updateProprietario(@RequestBody Proprietario proprietario) throws URISyntaxException {
        log.debug("REST request to update Proprietario : {}", proprietario);
        if (proprietario.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Proprietario result = proprietarioRepository.save(proprietario);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, proprietario.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /proprietarios} : get all the proprietarios.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of proprietarios in body.
     */
    @GetMapping("/proprietarios")
    public List<Proprietario> getAllProprietarios() {
        log.debug("REST request to get all Proprietarios");
        return proprietarioRepository.findAll();
    }

    /**
     * {@code GET  /proprietarios/:id} : get the "id" proprietario.
     *
     * @param id the id of the proprietario to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the proprietario, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proprietarios/{id}")
    public ResponseEntity<Proprietario> getProprietario(@PathVariable Long id) {
        log.debug("REST request to get Proprietario : {}", id);
        Optional<Proprietario> proprietario = proprietarioRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(proprietario);
    }

    /**
     * {@code DELETE  /proprietarios/:id} : delete the "id" proprietario.
     *
     * @param id the id of the proprietario to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proprietarios/{id}")
    public ResponseEntity<Void> deleteProprietario(@PathVariable Long id) {
        log.debug("REST request to delete Proprietario : {}", id);
        proprietarioRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
