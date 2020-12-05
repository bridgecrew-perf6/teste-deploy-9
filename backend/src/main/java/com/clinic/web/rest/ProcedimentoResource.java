package com.clinic.web.rest;

import com.clinic.domain.Procedimento;
import com.clinic.repository.ProcedimentoRepository;
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
 * REST controller for managing {@link com.clinic.domain.Procedimento}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ProcedimentoResource {

    private final Logger log = LoggerFactory.getLogger(ProcedimentoResource.class);

    private static final String ENTITY_NAME = "procedimento";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcedimentoRepository procedimentoRepository;

    public ProcedimentoResource(ProcedimentoRepository procedimentoRepository) {
        this.procedimentoRepository = procedimentoRepository;
    }

    /**
     * {@code POST  /procedimentos} : Create a new procedimento.
     *
     * @param procedimento the procedimento to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procedimento, or with status {@code 400 (Bad Request)} if the procedimento has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/procedimentos")
    public ResponseEntity<Procedimento> createProcedimento(@RequestBody Procedimento procedimento) throws URISyntaxException {
        log.debug("REST request to save Procedimento : {}", procedimento);
        if (procedimento.getId() != null) {
            throw new BadRequestAlertException("A new procedimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Procedimento result = procedimentoRepository.save(procedimento);
        return ResponseEntity.created(new URI("/api/procedimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /procedimentos} : Updates an existing procedimento.
     *
     * @param procedimento the procedimento to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procedimento,
     * or with status {@code 400 (Bad Request)} if the procedimento is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procedimento couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/procedimentos")
    public ResponseEntity<Procedimento> updateProcedimento(@RequestBody Procedimento procedimento) throws URISyntaxException {
        log.debug("REST request to update Procedimento : {}", procedimento);
        if (procedimento.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Procedimento result = procedimentoRepository.save(procedimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procedimento.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /procedimentos} : get all the procedimentos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procedimentos in body.
     */
    @GetMapping("/procedimentos")
    public List<Procedimento> getAllProcedimentos(@RequestParam(required = false) String filter) {
        if ("consulta-is-null".equals(filter)) {
            log.debug("REST request to get all Procedimentos where consulta is null");
            return StreamSupport
                .stream(procedimentoRepository.findAll().spliterator(), false)
                .filter(procedimento -> procedimento.getConsulta() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Procedimentos");
        return procedimentoRepository.findAll();
    }

    /**
     * {@code GET  /procedimentos/:id} : get the "id" procedimento.
     *
     * @param id the id of the procedimento to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procedimento, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/procedimentos/{id}")
    public ResponseEntity<Procedimento> getProcedimento(@PathVariable Long id) {
        log.debug("REST request to get Procedimento : {}", id);
        Optional<Procedimento> procedimento = procedimentoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(procedimento);
    }

    /**
     * {@code DELETE  /procedimentos/:id} : delete the "id" procedimento.
     *
     * @param id the id of the procedimento to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/procedimentos/{id}")
    public ResponseEntity<Void> deleteProcedimento(@PathVariable Long id) {
        log.debug("REST request to delete Procedimento : {}", id);
        procedimentoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
