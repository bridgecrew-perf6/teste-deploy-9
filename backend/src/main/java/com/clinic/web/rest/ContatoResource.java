package com.clinic.web.rest;

import com.clinic.domain.Contato;
import com.clinic.repository.ContatoRepository;
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
 * REST controller for managing {@link com.clinic.domain.Contato}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContatoResource {

    private final Logger log = LoggerFactory.getLogger(ContatoResource.class);

    private static final String ENTITY_NAME = "contato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContatoRepository contatoRepository;

    public ContatoResource(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    /**
     * {@code POST  /contatoes} : Create a new contato.
     *
     * @param contato the contato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contato, or with status {@code 400 (Bad Request)} if the contato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/contatoes")
    public ResponseEntity<Contato> createContato(@RequestBody Contato contato) throws URISyntaxException {
        log.debug("REST request to save Contato : {}", contato);
        if (contato.getId() != null) {
            throw new BadRequestAlertException("A new contato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Contato result = contatoRepository.save(contato);
        return ResponseEntity.created(new URI("/api/contatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /contatoes} : Updates an existing contato.
     *
     * @param contato the contato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contato,
     * or with status {@code 400 (Bad Request)} if the contato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/contatoes")
    public ResponseEntity<Contato> updateContato(@RequestBody Contato contato) throws URISyntaxException {
        log.debug("REST request to update Contato : {}", contato);
        if (contato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Contato result = contatoRepository.save(contato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contato.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /contatoes} : get all the contatoes.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contatoes in body.
     */
    @GetMapping("/contatoes")
    public List<Contato> getAllContatoes(@RequestParam(required = false) String filter) {
        if ("clinica-is-null".equals(filter)) {
            log.debug("REST request to get all Contatos where clinica is null");
            return StreamSupport
                .stream(contatoRepository.findAll().spliterator(), false)
                .filter(contato -> contato.getClinica() == null)
                .collect(Collectors.toList());
        }
        if ("paciente-is-null".equals(filter)) {
            log.debug("REST request to get all Contatos where paciente is null");
            return StreamSupport
                .stream(contatoRepository.findAll().spliterator(), false)
                .filter(contato -> contato.getPaciente() == null)
                .collect(Collectors.toList());
        }
        if ("profissional-is-null".equals(filter)) {
            log.debug("REST request to get all Contatos where profissional is null");
            return StreamSupport
                .stream(contatoRepository.findAll().spliterator(), false)
                .filter(contato -> contato.getProfissional() == null)
                .collect(Collectors.toList());
        }
        if ("funcionario-is-null".equals(filter)) {
            log.debug("REST request to get all Contatos where funcionario is null");
            return StreamSupport
                .stream(contatoRepository.findAll().spliterator(), false)
                .filter(contato -> contato.getFuncionario() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Contatoes");
        return contatoRepository.findAll();
    }

    /**
     * {@code GET  /contatoes/:id} : get the "id" contato.
     *
     * @param id the id of the contato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/contatoes/{id}")
    public ResponseEntity<Contato> getContato(@PathVariable Long id) {
        log.debug("REST request to get Contato : {}", id);
        Optional<Contato> contato = contatoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contato);
    }

    /**
     * {@code DELETE  /contatoes/:id} : delete the "id" contato.
     *
     * @param id the id of the contato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/contatoes/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        log.debug("REST request to delete Contato : {}", id);
        contatoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
