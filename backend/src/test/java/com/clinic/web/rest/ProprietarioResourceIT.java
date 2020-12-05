package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.Proprietario;
import com.clinic.repository.ProprietarioRepository;

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
 * Integration tests for the {@link ProprietarioResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ProprietarioResourceIT {

    @Autowired
    private ProprietarioRepository proprietarioRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProprietarioMockMvc;

    private Proprietario proprietario;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proprietario createEntity(EntityManager em) {
        Proprietario proprietario = new Proprietario();
        return proprietario;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Proprietario createUpdatedEntity(EntityManager em) {
        Proprietario proprietario = new Proprietario();
        return proprietario;
    }

    @BeforeEach
    public void initTest() {
        proprietario = createEntity(em);
    }

    @Test
    @Transactional
    public void createProprietario() throws Exception {
        int databaseSizeBeforeCreate = proprietarioRepository.findAll().size();

        // Create the Proprietario
        restProprietarioMockMvc.perform(post("/api/proprietarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proprietario)))
            .andExpect(status().isCreated());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeCreate + 1);
        Proprietario testProprietario = proprietarioList.get(proprietarioList.size() - 1);
    }

    @Test
    @Transactional
    public void createProprietarioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = proprietarioRepository.findAll().size();

        // Create the Proprietario with an existing ID
        proprietario.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProprietarioMockMvc.perform(post("/api/proprietarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proprietario)))
            .andExpect(status().isBadRequest());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProprietarios() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get all the proprietarioList
        restProprietarioMockMvc.perform(get("/api/proprietarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(proprietario.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getProprietario() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        // Get the proprietario
        restProprietarioMockMvc.perform(get("/api/proprietarios/{id}", proprietario.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(proprietario.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProprietario() throws Exception {
        // Get the proprietario
        restProprietarioMockMvc.perform(get("/api/proprietarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProprietario() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();

        // Update the proprietario
        Proprietario updatedProprietario = proprietarioRepository.findById(proprietario.getId()).get();
        // Disconnect from session so that the updates on updatedProprietario are not directly saved in db
        em.detach(updatedProprietario);

        restProprietarioMockMvc.perform(put("/api/proprietarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProprietario)))
            .andExpect(status().isOk());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
        Proprietario testProprietario = proprietarioList.get(proprietarioList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingProprietario() throws Exception {
        int databaseSizeBeforeUpdate = proprietarioRepository.findAll().size();

        // Create the Proprietario

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProprietarioMockMvc.perform(put("/api/proprietarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(proprietario)))
            .andExpect(status().isBadRequest());

        // Validate the Proprietario in the database
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProprietario() throws Exception {
        // Initialize the database
        proprietarioRepository.saveAndFlush(proprietario);

        int databaseSizeBeforeDelete = proprietarioRepository.findAll().size();

        // Delete the proprietario
        restProprietarioMockMvc.perform(delete("/api/proprietarios/{id}", proprietario.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Proprietario> proprietarioList = proprietarioRepository.findAll();
        assertThat(proprietarioList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
