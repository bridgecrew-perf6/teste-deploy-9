package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.Consulta;
import com.clinic.repository.ConsultaRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.clinic.domain.enumeration.StatusConsulta;
/**
 * Integration tests for the {@link ConsultaResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class ConsultaResourceIT {

    private static final Instant DEFAULT_DATA_CONSULTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_CONSULTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_ID_PROFISSIONAL = 1L;
    private static final Long UPDATED_ID_PROFISSIONAL = 2L;

    private static final Long DEFAULT_ID_PACIENTE = 1L;
    private static final Long UPDATED_ID_PACIENTE = 2L;

    private static final Boolean DEFAULT_STATUS_PAGAMENTO = false;
    private static final Boolean UPDATED_STATUS_PAGAMENTO = true;

    private static final StatusConsulta DEFAULT_STATUS_CONSULTA = StatusConsulta.CRIADO;
    private static final StatusConsulta UPDATED_STATUS_CONSULTA = StatusConsulta.ANGENDADO;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultaMockMvc;

    private Consulta consulta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consulta createEntity(EntityManager em) {
        Consulta consulta = new Consulta()
            .dataConsulta(DEFAULT_DATA_CONSULTA)
            .idProfissional(DEFAULT_ID_PROFISSIONAL)
            .idPaciente(DEFAULT_ID_PACIENTE)
            .statusPagamento(DEFAULT_STATUS_PAGAMENTO)
            .statusConsulta(DEFAULT_STATUS_CONSULTA);
        return consulta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Consulta createUpdatedEntity(EntityManager em) {
        Consulta consulta = new Consulta()
            .dataConsulta(UPDATED_DATA_CONSULTA)
            .idProfissional(UPDATED_ID_PROFISSIONAL)
            .idPaciente(UPDATED_ID_PACIENTE)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .statusConsulta(UPDATED_STATUS_CONSULTA);
        return consulta;
    }

    @BeforeEach
    public void initTest() {
        consulta = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsulta() throws Exception {
        int databaseSizeBeforeCreate = consultaRepository.findAll().size();

        // Create the Consulta
        restConsultaMockMvc.perform(post("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isCreated());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeCreate + 1);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getDataConsulta()).isEqualTo(DEFAULT_DATA_CONSULTA);
        assertThat(testConsulta.getIdProfissional()).isEqualTo(DEFAULT_ID_PROFISSIONAL);
        assertThat(testConsulta.getIdPaciente()).isEqualTo(DEFAULT_ID_PACIENTE);
        assertThat(testConsulta.isStatusPagamento()).isEqualTo(DEFAULT_STATUS_PAGAMENTO);
        assertThat(testConsulta.getStatusConsulta()).isEqualTo(DEFAULT_STATUS_CONSULTA);
    }

    @Test
    @Transactional
    public void createConsultaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultaRepository.findAll().size();

        // Create the Consulta with an existing ID
        consulta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultaMockMvc.perform(post("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDataConsultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaRepository.findAll().size();
        // set the field null
        consulta.setDataConsulta(null);

        // Create the Consulta, which fails.

        restConsultaMockMvc.perform(post("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isBadRequest());

        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdProfissionalIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaRepository.findAll().size();
        // set the field null
        consulta.setIdProfissional(null);

        // Create the Consulta, which fails.

        restConsultaMockMvc.perform(post("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isBadRequest());

        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIdPacienteIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaRepository.findAll().size();
        // set the field null
        consulta.setIdPaciente(null);

        // Create the Consulta, which fails.

        restConsultaMockMvc.perform(post("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isBadRequest());

        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsultas() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get all the consultaList
        restConsultaMockMvc.perform(get("/api/consultas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consulta.getId().intValue())))
            .andExpect(jsonPath("$.[*].dataConsulta").value(hasItem(DEFAULT_DATA_CONSULTA.toString())))
            .andExpect(jsonPath("$.[*].idProfissional").value(hasItem(DEFAULT_ID_PROFISSIONAL.intValue())))
            .andExpect(jsonPath("$.[*].idPaciente").value(hasItem(DEFAULT_ID_PACIENTE.intValue())))
            .andExpect(jsonPath("$.[*].statusPagamento").value(hasItem(DEFAULT_STATUS_PAGAMENTO.booleanValue())))
            .andExpect(jsonPath("$.[*].statusConsulta").value(hasItem(DEFAULT_STATUS_CONSULTA.toString())));
    }
    
    @Test
    @Transactional
    public void getConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        // Get the consulta
        restConsultaMockMvc.perform(get("/api/consultas/{id}", consulta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consulta.getId().intValue()))
            .andExpect(jsonPath("$.dataConsulta").value(DEFAULT_DATA_CONSULTA.toString()))
            .andExpect(jsonPath("$.idProfissional").value(DEFAULT_ID_PROFISSIONAL.intValue()))
            .andExpect(jsonPath("$.idPaciente").value(DEFAULT_ID_PACIENTE.intValue()))
            .andExpect(jsonPath("$.statusPagamento").value(DEFAULT_STATUS_PAGAMENTO.booleanValue()))
            .andExpect(jsonPath("$.statusConsulta").value(DEFAULT_STATUS_CONSULTA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsulta() throws Exception {
        // Get the consulta
        restConsultaMockMvc.perform(get("/api/consultas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Update the consulta
        Consulta updatedConsulta = consultaRepository.findById(consulta.getId()).get();
        // Disconnect from session so that the updates on updatedConsulta are not directly saved in db
        em.detach(updatedConsulta);
        updatedConsulta
            .dataConsulta(UPDATED_DATA_CONSULTA)
            .idProfissional(UPDATED_ID_PROFISSIONAL)
            .idPaciente(UPDATED_ID_PACIENTE)
            .statusPagamento(UPDATED_STATUS_PAGAMENTO)
            .statusConsulta(UPDATED_STATUS_CONSULTA);

        restConsultaMockMvc.perform(put("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsulta)))
            .andExpect(status().isOk());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
        Consulta testConsulta = consultaList.get(consultaList.size() - 1);
        assertThat(testConsulta.getDataConsulta()).isEqualTo(UPDATED_DATA_CONSULTA);
        assertThat(testConsulta.getIdProfissional()).isEqualTo(UPDATED_ID_PROFISSIONAL);
        assertThat(testConsulta.getIdPaciente()).isEqualTo(UPDATED_ID_PACIENTE);
        assertThat(testConsulta.isStatusPagamento()).isEqualTo(UPDATED_STATUS_PAGAMENTO);
        assertThat(testConsulta.getStatusConsulta()).isEqualTo(UPDATED_STATUS_CONSULTA);
    }

    @Test
    @Transactional
    public void updateNonExistingConsulta() throws Exception {
        int databaseSizeBeforeUpdate = consultaRepository.findAll().size();

        // Create the Consulta

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultaMockMvc.perform(put("/api/consultas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(consulta)))
            .andExpect(status().isBadRequest());

        // Validate the Consulta in the database
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsulta() throws Exception {
        // Initialize the database
        consultaRepository.saveAndFlush(consulta);

        int databaseSizeBeforeDelete = consultaRepository.findAll().size();

        // Delete the consulta
        restConsultaMockMvc.perform(delete("/api/consultas/{id}", consulta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Consulta> consultaList = consultaRepository.findAll();
        assertThat(consultaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
