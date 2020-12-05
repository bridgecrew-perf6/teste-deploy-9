package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.Procedimento;
import com.clinic.repository.ProcedimentoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProcedimentoResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProcedimentoResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final String DEFAULT_CID = "AAAAAAAAAA";
    private static final String UPDATED_CID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private ProcedimentoRepository procedimentoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcedimentoMockMvc;

    private Procedimento procedimento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedimento createEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento()
            .descricao(DEFAULT_DESCRICAO)
            .cid(DEFAULT_CID)
            .valor(DEFAULT_VALOR);
        return procedimento;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Procedimento createUpdatedEntity(EntityManager em) {
        Procedimento procedimento = new Procedimento()
            .descricao(UPDATED_DESCRICAO)
            .cid(UPDATED_CID)
            .valor(UPDATED_VALOR);
        return procedimento;
    }

    @BeforeEach
    public void initTest() {
        procedimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedimento() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // Create the Procedimento
        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(procedimento)))
            .andExpect(status().isCreated());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testProcedimento.getCid()).isEqualTo(DEFAULT_CID);
        assertThat(testProcedimento.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createProcedimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedimentoRepository.findAll().size();

        // Create the Procedimento with an existing ID
        procedimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedimentoMockMvc.perform(post("/api/procedimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(procedimento)))
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProcedimentos() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get all the procedimentoList
        restProcedimentoMockMvc.perform(get("/api/procedimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].cid").value(hasItem(DEFAULT_CID)))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }
    
    @Test
    @Transactional
    public void getProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", procedimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procedimento.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.cid").value(DEFAULT_CID))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProcedimento() throws Exception {
        // Get the procedimento
        restProcedimentoMockMvc.perform(get("/api/procedimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Update the procedimento
        Procedimento updatedProcedimento = procedimentoRepository.findById(procedimento.getId()).get();
        // Disconnect from session so that the updates on updatedProcedimento are not directly saved in db
        em.detach(updatedProcedimento);
        updatedProcedimento
            .descricao(UPDATED_DESCRICAO)
            .cid(UPDATED_CID)
            .valor(UPDATED_VALOR);

        restProcedimentoMockMvc.perform(put("/api/procedimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcedimento)))
            .andExpect(status().isOk());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
        Procedimento testProcedimento = procedimentoList.get(procedimentoList.size() - 1);
        assertThat(testProcedimento.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testProcedimento.getCid()).isEqualTo(UPDATED_CID);
        assertThat(testProcedimento.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedimento() throws Exception {
        int databaseSizeBeforeUpdate = procedimentoRepository.findAll().size();

        // Create the Procedimento

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedimentoMockMvc.perform(put("/api/procedimentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(procedimento)))
            .andExpect(status().isBadRequest());

        // Validate the Procedimento in the database
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcedimento() throws Exception {
        // Initialize the database
        procedimentoRepository.saveAndFlush(procedimento);

        int databaseSizeBeforeDelete = procedimentoRepository.findAll().size();

        // Delete the procedimento
        restProcedimentoMockMvc.perform(delete("/api/procedimentos/{id}", procedimento.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Procedimento> procedimentoList = procedimentoRepository.findAll();
        assertThat(procedimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
