package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.Profissional;
import com.clinic.repository.ProfissionalRepository;

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
 * Integration tests for the {@link ProfissionalResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProfissionalResourceIT {

    private static final String DEFAULT_CFO = "AAAAAAAAAA";
    private static final String UPDATED_CFO = "BBBBBBBBBB";

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfissionalMockMvc;

    private Profissional profissional;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissional createEntity(EntityManager em) {
        Profissional profissional = new Profissional()
            .cfo(DEFAULT_CFO);
        return profissional;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Profissional createUpdatedEntity(EntityManager em) {
        Profissional profissional = new Profissional()
            .cfo(UPDATED_CFO);
        return profissional;
    }

    @BeforeEach
    public void initTest() {
        profissional = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfissional() throws Exception {
        int databaseSizeBeforeCreate = profissionalRepository.findAll().size();

        // Create the Profissional
        restProfissionalMockMvc.perform(post("/api/profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profissional)))
            .andExpect(status().isCreated());

        // Validate the Profissional in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeCreate + 1);
        Profissional testProfissional = profissionalList.get(profissionalList.size() - 1);
        assertThat(testProfissional.getCfo()).isEqualTo(DEFAULT_CFO);
    }

    @Test
    @Transactional
    public void createProfissionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profissionalRepository.findAll().size();

        // Create the Profissional with an existing ID
        profissional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfissionalMockMvc.perform(post("/api/profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profissional)))
            .andExpect(status().isBadRequest());

        // Validate the Profissional in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProfissionals() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);

        // Get all the profissionalList
        restProfissionalMockMvc.perform(get("/api/profissionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profissional.getId().intValue())))
            .andExpect(jsonPath("$.[*].cfo").value(hasItem(DEFAULT_CFO)));
    }
    
    @Test
    @Transactional
    public void getProfissional() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);

        // Get the profissional
        restProfissionalMockMvc.perform(get("/api/profissionals/{id}", profissional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(profissional.getId().intValue()))
            .andExpect(jsonPath("$.cfo").value(DEFAULT_CFO));
    }

    @Test
    @Transactional
    public void getNonExistingProfissional() throws Exception {
        // Get the profissional
        restProfissionalMockMvc.perform(get("/api/profissionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfissional() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);

        int databaseSizeBeforeUpdate = profissionalRepository.findAll().size();

        // Update the profissional
        Profissional updatedProfissional = profissionalRepository.findById(profissional.getId()).get();
        // Disconnect from session so that the updates on updatedProfissional are not directly saved in db
        em.detach(updatedProfissional);
        updatedProfissional
            .cfo(UPDATED_CFO);

        restProfissionalMockMvc.perform(put("/api/profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfissional)))
            .andExpect(status().isOk());

        // Validate the Profissional in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeUpdate);
        Profissional testProfissional = profissionalList.get(profissionalList.size() - 1);
        assertThat(testProfissional.getCfo()).isEqualTo(UPDATED_CFO);
    }

    @Test
    @Transactional
    public void updateNonExistingProfissional() throws Exception {
        int databaseSizeBeforeUpdate = profissionalRepository.findAll().size();

        // Create the Profissional

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfissionalMockMvc.perform(put("/api/profissionals")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(profissional)))
            .andExpect(status().isBadRequest());

        // Validate the Profissional in the database
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfissional() throws Exception {
        // Initialize the database
        profissionalRepository.saveAndFlush(profissional);

        int databaseSizeBeforeDelete = profissionalRepository.findAll().size();

        // Delete the profissional
        restProfissionalMockMvc.perform(delete("/api/profissionals/{id}", profissional.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Profissional> profissionalList = profissionalRepository.findAll();
        assertThat(profissionalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
