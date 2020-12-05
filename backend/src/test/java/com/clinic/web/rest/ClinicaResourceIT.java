package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.Clinica;
import com.clinic.repository.ClinicaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ClinicaResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ClinicaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Long DEFAULT_CNPJ = 1L;
    private static final Long UPDATED_CNPJ = 2L;

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Mock
    private ClinicaRepository clinicaRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClinicaMockMvc;

    private Clinica clinica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinica createEntity(EntityManager em) {
        Clinica clinica = new Clinica()
            .nome(DEFAULT_NOME)
            .cnpj(DEFAULT_CNPJ)
            .data(DEFAULT_DATA);
        return clinica;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clinica createUpdatedEntity(EntityManager em) {
        Clinica clinica = new Clinica()
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .data(UPDATED_DATA);
        return clinica;
    }

    @BeforeEach
    public void initTest() {
        clinica = createEntity(em);
    }

    @Test
    @Transactional
    public void createClinica() throws Exception {
        int databaseSizeBeforeCreate = clinicaRepository.findAll().size();

        // Create the Clinica
        restClinicaMockMvc.perform(post("/api/clinicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clinica)))
            .andExpect(status().isCreated());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeCreate + 1);
        Clinica testClinica = clinicaList.get(clinicaList.size() - 1);
        assertThat(testClinica.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testClinica.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testClinica.getData()).isEqualTo(DEFAULT_DATA);
    }

    @Test
    @Transactional
    public void createClinicaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clinicaRepository.findAll().size();

        // Create the Clinica with an existing ID
        clinica.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClinicaMockMvc.perform(post("/api/clinicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clinica)))
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicaRepository.findAll().size();
        // set the field null
        clinica.setNome(null);

        // Create the Clinica, which fails.

        restClinicaMockMvc.perform(post("/api/clinicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clinica)))
            .andExpect(status().isBadRequest());

        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCnpjIsRequired() throws Exception {
        int databaseSizeBeforeTest = clinicaRepository.findAll().size();
        // set the field null
        clinica.setCnpj(null);

        // Create the Clinica, which fails.

        restClinicaMockMvc.perform(post("/api/clinicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clinica)))
            .andExpect(status().isBadRequest());

        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClinicas() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        // Get all the clinicaList
        restClinicaMockMvc.perform(get("/api/clinicas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clinica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllClinicasWithEagerRelationshipsIsEnabled() throws Exception {
        ClinicaResource clinicaResource = new ClinicaResource(clinicaRepositoryMock);
        when(clinicaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClinicaMockMvc.perform(get("/api/clinicas?eagerload=true"))
            .andExpect(status().isOk());

        verify(clinicaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllClinicasWithEagerRelationshipsIsNotEnabled() throws Exception {
        ClinicaResource clinicaResource = new ClinicaResource(clinicaRepositoryMock);
        when(clinicaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restClinicaMockMvc.perform(get("/api/clinicas?eagerload=true"))
            .andExpect(status().isOk());

        verify(clinicaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getClinica() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        // Get the clinica
        restClinicaMockMvc.perform(get("/api/clinicas/{id}", clinica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clinica.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingClinica() throws Exception {
        // Get the clinica
        restClinicaMockMvc.perform(get("/api/clinicas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClinica() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        int databaseSizeBeforeUpdate = clinicaRepository.findAll().size();

        // Update the clinica
        Clinica updatedClinica = clinicaRepository.findById(clinica.getId()).get();
        // Disconnect from session so that the updates on updatedClinica are not directly saved in db
        em.detach(updatedClinica);
        updatedClinica
            .nome(UPDATED_NOME)
            .cnpj(UPDATED_CNPJ)
            .data(UPDATED_DATA);

        restClinicaMockMvc.perform(put("/api/clinicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedClinica)))
            .andExpect(status().isOk());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeUpdate);
        Clinica testClinica = clinicaList.get(clinicaList.size() - 1);
        assertThat(testClinica.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testClinica.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testClinica.getData()).isEqualTo(UPDATED_DATA);
    }

    @Test
    @Transactional
    public void updateNonExistingClinica() throws Exception {
        int databaseSizeBeforeUpdate = clinicaRepository.findAll().size();

        // Create the Clinica

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClinicaMockMvc.perform(put("/api/clinicas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(clinica)))
            .andExpect(status().isBadRequest());

        // Validate the Clinica in the database
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClinica() throws Exception {
        // Initialize the database
        clinicaRepository.saveAndFlush(clinica);

        int databaseSizeBeforeDelete = clinicaRepository.findAll().size();

        // Delete the clinica
        restClinicaMockMvc.perform(delete("/api/clinicas/{id}", clinica.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clinica> clinicaList = clinicaRepository.findAll();
        assertThat(clinicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
