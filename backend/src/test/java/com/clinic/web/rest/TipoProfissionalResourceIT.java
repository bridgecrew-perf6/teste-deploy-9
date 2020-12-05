package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.TipoProfissional;
import com.clinic.repository.TipoProfissionalRepository;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoProfissionalResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class TipoProfissionalResourceIT {

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    @Autowired
    private TipoProfissionalRepository tipoProfissionalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoProfissionalMockMvc;

    private TipoProfissional tipoProfissional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProfissional createEntity(EntityManager em) {
        TipoProfissional tipoProfissional = new TipoProfissional()
            .descricao(DEFAULT_DESCRICAO);
        return tipoProfissional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProfissional createUpdatedEntity(EntityManager em) {
        TipoProfissional tipoProfissional = new TipoProfissional()
            .descricao(UPDATED_DESCRICAO);
        return tipoProfissional;
    }

    @BeforeEach
    public void initTest() {
        tipoProfissional = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoProfissional() throws Exception {
        int databaseSizeBeforeCreate = tipoProfissionalRepository.findAll().size();

        // Create the TipoProfissional
        restTipoProfissionalMockMvc.perform(post("/api/tipo-profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProfissional)))
            .andExpect(status().isCreated());

        // Validate the TipoProfissional in the database
        List<TipoProfissional> tipoProfissionalList = tipoProfissionalRepository.findAll();
        assertThat(tipoProfissionalList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProfissional testTipoProfissional = tipoProfissionalList.get(tipoProfissionalList.size() - 1);
        assertThat(testTipoProfissional.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
    }

    @Test
    @Transactional
    public void createTipoProfissionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoProfissionalRepository.findAll().size();

        // Create the TipoProfissional with an existing ID
        tipoProfissional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProfissionalMockMvc.perform(post("/api/tipo-profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProfissional)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProfissional in the database
        List<TipoProfissional> tipoProfissionalList = tipoProfissionalRepository.findAll();
        assertThat(tipoProfissionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoProfissionals() throws Exception {
        // Initialize the database
        tipoProfissionalRepository.saveAndFlush(tipoProfissional);

        // Get all the tipoProfissionalList
        restTipoProfissionalMockMvc.perform(get("/api/tipo-profissionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProfissional.getId().intValue())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)));
    }
    
    @Test
    @Transactional
    public void getTipoProfissional() throws Exception {
        // Initialize the database
        tipoProfissionalRepository.saveAndFlush(tipoProfissional);

        // Get the tipoProfissional
        restTipoProfissionalMockMvc.perform(get("/api/tipo-profissionals/{id}", tipoProfissional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProfissional.getId().intValue()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoProfissional() throws Exception {
        // Get the tipoProfissional
        restTipoProfissionalMockMvc.perform(get("/api/tipo-profissionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoProfissional() throws Exception {
        // Initialize the database
        tipoProfissionalRepository.saveAndFlush(tipoProfissional);

        int databaseSizeBeforeUpdate = tipoProfissionalRepository.findAll().size();

        // Update the tipoProfissional
        TipoProfissional updatedTipoProfissional = tipoProfissionalRepository.findById(tipoProfissional.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProfissional are not directly saved in db
        em.detach(updatedTipoProfissional);
        updatedTipoProfissional
            .descricao(UPDATED_DESCRICAO);

        restTipoProfissionalMockMvc.perform(put("/api/tipo-profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoProfissional)))
            .andExpect(status().isOk());

        // Validate the TipoProfissional in the database
        List<TipoProfissional> tipoProfissionalList = tipoProfissionalRepository.findAll();
        assertThat(tipoProfissionalList).hasSize(databaseSizeBeforeUpdate);
        TipoProfissional testTipoProfissional = tipoProfissionalList.get(tipoProfissionalList.size() - 1);
        assertThat(testTipoProfissional.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoProfissional() throws Exception {
        int databaseSizeBeforeUpdate = tipoProfissionalRepository.findAll().size();

        // Create the TipoProfissional

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProfissionalMockMvc.perform(put("/api/tipo-profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoProfissional)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProfissional in the database
        List<TipoProfissional> tipoProfissionalList = tipoProfissionalRepository.findAll();
        assertThat(tipoProfissionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoProfissional() throws Exception {
        // Initialize the database
        tipoProfissionalRepository.saveAndFlush(tipoProfissional);

        int databaseSizeBeforeDelete = tipoProfissionalRepository.findAll().size();

        // Delete the tipoProfissional
        restTipoProfissionalMockMvc.perform(delete("/api/tipo-profissionals/{id}", tipoProfissional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoProfissional> tipoProfissionalList = tipoProfissionalRepository.findAll();
        assertThat(tipoProfissionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
