package com.clinic.web.rest;

import com.clinic.domain.DadosBasicos;
import com.clinic.repository.DadosBasicosRepository;
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
 * REST controller for managing {@link com.clinic.domain.DadosBasicos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DadosBasicosResource {

    private final Logger log = LoggerFactory.getLogger(DadosBasicosResource.class);

    private static final String ENTITY_NAME = "dadosBasicos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DadosBasicosRepository dadosBasicosRepository;

    public DadosBasicosResource(DadosBasicosRepository dadosBasicosRepository) {
        this.dadosBasicosRepository = dadosBasicosRepository;
    }

    /**
     * {@code POST  /dados-basicos} : Create a new dadosBasicos.
     *
     * @param dadosBasicos the dadosBasicos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dadosBasicos, or with status {@code 400 (Bad Request)} if the dadosBasicos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dados-basicos")
    public ResponseEntity<DadosBasicos> createDadosBasicos(@RequestBody DadosBasicos dadosBasicos) throws URISyntaxException {
        log.debug("REST request to save DadosBasicos : {}", dadosBasicos);
        if (dadosBasicos.getId() != null) {
            throw new BadRequestAlertException("A new dadosBasicos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DadosBasicos result = dadosBasicosRepository.save(dadosBasicos);
        return ResponseEntity.created(new URI("/api/dados-basicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dados-basicos} : Updates an existing dadosBasicos.
     *
     * @param dadosBasicos the dadosBasicos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dadosBasicos,
     * or with status {@code 400 (Bad Request)} if the dadosBasicos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dadosBasicos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dados-basicos")
    public ResponseEntity<DadosBasicos> updateDadosBasicos(@RequestBody DadosBasicos dadosBasicos) throws URISyntaxException {
        log.debug("REST request to update DadosBasicos : {}", dadosBasicos);
        if (dadosBasicos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DadosBasicos result = dadosBasicosRepository.save(dadosBasicos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dadosBasicos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dados-basicos} : get all the dadosBasicos.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dadosBasicos in body.
     */
    @GetMapping("/dados-basicos")
    public List<DadosBasicos> getAllDadosBasicos(@RequestParam(required = false) String filter) {
        if ("paciente-is-null".equals(filter)) {
            log.debug("REST request to get all DadosBasicoss where paciente is null");
            return StreamSupport
                .stream(dadosBasicosRepository.findAll().spliterator(), false)
                .filter(dadosBasicos -> dadosBasicos.getPaciente() == null)
                .collect(Collectors.toList());
        }
        if ("profissional-is-null".equals(filter)) {
            log.debug("REST request to get all DadosBasicoss where profissional is null");
            return StreamSupport
                .stream(dadosBasicosRepository.findAll().spliterator(), false)
                .filter(dadosBasicos -> dadosBasicos.getProfissional() == null)
                .collect(Collectors.toList());
        }
        if ("funcionario-is-null".equals(filter)) {
            log.debug("REST request to get all DadosBasicoss where funcionario is null");
            return StreamSupport
                .stream(dadosBasicosRepository.findAll().spliterator(), false)
                .filter(dadosBasicos -> dadosBasicos.getFuncionario() == null)
                .collect(Collectors.toList());
        }
        if ("proprietario-is-null".equals(filter)) {
            log.debug("REST request to get all DadosBasicoss where proprietario is null");
            return StreamSupport
                .stream(dadosBasicosRepository.findAll().spliterator(), false)
                .filter(dadosBasicos -> dadosBasicos.getProprietario() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all DadosBasicos");
        return dadosBasicosRepository.findAll();
    }

    /**
     * {@code GET  /dados-basicos/:id} : get the "id" dadosBasicos.
     *
     * @param id the id of the dadosBasicos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dadosBasicos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dados-basicos/{id}")
    public ResponseEntity<DadosBasicos> getDadosBasicos(@PathVariable Long id) {
        log.debug("REST request to get DadosBasicos : {}", id);
        Optional<DadosBasicos> dadosBasicos = dadosBasicosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dadosBasicos);
    }

    /**
     * {@code DELETE  /dados-basicos/:id} : delete the "id" dadosBasicos.
     *
     * @param id the id of the dadosBasicos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dados-basicos/{id}")
    public ResponseEntity<Void> deleteDadosBasicos(@PathVariable Long id) {
        log.debug("REST request to delete DadosBasicos : {}", id);
        dadosBasicosRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
