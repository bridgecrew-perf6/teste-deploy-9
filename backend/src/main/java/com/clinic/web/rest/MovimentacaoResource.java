package com.clinic.web.rest;

import com.clinic.domain.Movimentacao;
import com.clinic.repository.MovimentacaoRepository;
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
 * REST controller for managing {@link com.clinic.domain.Movimentacao}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MovimentacaoResource {

    private final Logger log = LoggerFactory.getLogger(MovimentacaoResource.class);

    private static final String ENTITY_NAME = "movimentacao";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MovimentacaoRepository movimentacaoRepository;

    public MovimentacaoResource(MovimentacaoRepository movimentacaoRepository) {
        this.movimentacaoRepository = movimentacaoRepository;
    }

    /**
     * {@code POST  /movimentacaos} : Create a new movimentacao.
     *
     * @param movimentacao the movimentacao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new movimentacao, or with status {@code 400 (Bad Request)} if the movimentacao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/movimentacaos")
    public ResponseEntity<Movimentacao> createMovimentacao(@Valid @RequestBody Movimentacao movimentacao) throws URISyntaxException {
        log.debug("REST request to save Movimentacao : {}", movimentacao);
        if (movimentacao.getId() != null) {
            throw new BadRequestAlertException("A new movimentacao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Movimentacao result = movimentacaoRepository.save(movimentacao);
        return ResponseEntity.created(new URI("/api/movimentacaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /movimentacaos} : Updates an existing movimentacao.
     *
     * @param movimentacao the movimentacao to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated movimentacao,
     * or with status {@code 400 (Bad Request)} if the movimentacao is not valid,
     * or with status {@code 500 (Internal Server Error)} if the movimentacao couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/movimentacaos")
    public ResponseEntity<Movimentacao> updateMovimentacao(@Valid @RequestBody Movimentacao movimentacao) throws URISyntaxException {
        log.debug("REST request to update Movimentacao : {}", movimentacao);
        if (movimentacao.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Movimentacao result = movimentacaoRepository.save(movimentacao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, movimentacao.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /movimentacaos} : get all the movimentacaos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of movimentacaos in body.
     */
    @GetMapping("/movimentacaos")
    public List<Movimentacao> getAllMovimentacaos() {
        log.debug("REST request to get all Movimentacaos");
        return movimentacaoRepository.findAll();
    }

    /**
     * {@code GET  /movimentacaos/:id} : get the "id" movimentacao.
     *
     * @param id the id of the movimentacao to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the movimentacao, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/movimentacaos/{id}")
    public ResponseEntity<Movimentacao> getMovimentacao(@PathVariable Long id) {
        log.debug("REST request to get Movimentacao : {}", id);
        Optional<Movimentacao> movimentacao = movimentacaoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(movimentacao);
    }

    /**
     * {@code DELETE  /movimentacaos/:id} : delete the "id" movimentacao.
     *
     * @param id the id of the movimentacao to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/movimentacaos/{id}")
    public ResponseEntity<Void> deleteMovimentacao(@PathVariable Long id) {
        log.debug("REST request to delete Movimentacao : {}", id);
        movimentacaoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
