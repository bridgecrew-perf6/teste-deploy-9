package com.clinic.web.rest;

import com.clinic.domain.TipoProfissional;
import com.clinic.repository.TipoProfissionalRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.clinic.domain.TipoProfissional}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TipoProfissionalResource {

    private final Logger log = LoggerFactory.getLogger(TipoProfissionalResource.class);

    private static final String ENTITY_NAME = "tipoProfissional";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoProfissionalRepository tipoProfissionalRepository;

    public TipoProfissionalResource(TipoProfissionalRepository tipoProfissionalRepository) {
        this.tipoProfissionalRepository = tipoProfissionalRepository;
    }

    /**
     * {@code POST  /tipo-profissionals} : Create a new tipoProfissional.
     *
     * @param tipoProfissional the tipoProfissional to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoProfissional, or with status {@code 400 (Bad Request)} if the tipoProfissional has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-profissionals")
    public ResponseEntity<TipoProfissional> createTipoProfissional(@RequestBody TipoProfissional tipoProfissional) throws URISyntaxException {
        log.debug("REST request to save TipoProfissional : {}", tipoProfissional);
        if (tipoProfissional.getId() != null) {
            throw new BadRequestAlertException("A new tipoProfissional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoProfissional result = tipoProfissionalRepository.save(tipoProfissional);
        return ResponseEntity.created(new URI("/api/tipo-profissionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-profissionals} : Updates an existing tipoProfissional.
     *
     * @param tipoProfissional the tipoProfissional to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoProfissional,
     * or with status {@code 400 (Bad Request)} if the tipoProfissional is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoProfissional couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-profissionals")
    public ResponseEntity<TipoProfissional> updateTipoProfissional(@RequestBody TipoProfissional tipoProfissional) throws URISyntaxException {
        log.debug("REST request to update TipoProfissional : {}", tipoProfissional);
        if (tipoProfissional.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoProfissional result = tipoProfissionalRepository.save(tipoProfissional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoProfissional.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-profissionals} : get all the tipoProfissionals.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoProfissionals in body.
     */
    @GetMapping("/tipo-profissionals")
    public List<TipoProfissional> getAllTipoProfissionals(@RequestParam(required = false) String filter) {
        if ("profissional-is-null".equals(filter)) {
            log.debug("REST request to get all TipoProfissionals where profissional is null");
            return StreamSupport
                .stream(tipoProfissionalRepository.findAll().spliterator(), false)
                .filter(tipoProfissional -> tipoProfissional.getProfissional() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all TipoProfissionals");
        return tipoProfissionalRepository.findAll();
    }

    /**
     * {@code GET  /tipo-profissionals/:id} : get the "id" tipoProfissional.
     *
     * @param id the id of the tipoProfissional to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoProfissional, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-profissionals/{id}")
    public ResponseEntity<TipoProfissional> getTipoProfissional(@PathVariable Long id) {
        log.debug("REST request to get TipoProfissional : {}", id);
        Optional<TipoProfissional> tipoProfissional = tipoProfissionalRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoProfissional);
    }

    /**
     * {@code DELETE  /tipo-profissionals/:id} : delete the "id" tipoProfissional.
     *
     * @param id the id of the tipoProfissional to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-profissionals/{id}")
    public ResponseEntity<Void> deleteTipoProfissional(@PathVariable Long id) {
        log.debug("REST request to delete TipoProfissional : {}", id);
        tipoProfissionalRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
