package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.DadosBasicos;
import com.clinic.repository.DadosBasicosRepository;

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

import com.clinic.domain.enumeration.Sexo;
/**
 * Integration tests for the {@link DadosBasicosResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DadosBasicosResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATA_NASCIMENTO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_NASCIMENTO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CPF = 1L;
    private static final Long UPDATED_CPF = 2L;

    private static final Long DEFAULT_IDENTIDADE = 1L;
    private static final Long UPDATED_IDENTIDADE = 2L;

    private static final String DEFAULT_PAI = "AAAAAAAAAA";
    private static final String UPDATED_PAI = "BBBBBBBBBB";

    private static final String DEFAULT_MAE = "AAAAAAAAAA";
    private static final String UPDATED_MAE = "BBBBBBBBBB";

    private static final String DEFAULT_FOTO = "AAAAAAAAAA";
    private static final String UPDATED_FOTO = "BBBBBBBBBB";

    private static final Sexo DEFAULT_SEXO = Sexo.MASCULINO;
    private static final Sexo UPDATED_SEXO = Sexo.FEMININO;

    @Autowired
    private DadosBasicosRepository dadosBasicosRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDadosBasicosMockMvc;

    private DadosBasicos dadosBasicos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosBasicos createEntity(EntityManager em) {
        DadosBasicos dadosBasicos = new DadosBasicos()
            .nome(DEFAULT_NOME)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .data(DEFAULT_DATA)
            .cpf(DEFAULT_CPF)
            .identidade(DEFAULT_IDENTIDADE)
            .pai(DEFAULT_PAI)
            .mae(DEFAULT_MAE)
            .foto(DEFAULT_FOTO)
            .sexo(DEFAULT_SEXO);
        return dadosBasicos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DadosBasicos createUpdatedEntity(EntityManager em) {
        DadosBasicos dadosBasicos = new DadosBasicos()
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .data(UPDATED_DATA)
            .cpf(UPDATED_CPF)
            .identidade(UPDATED_IDENTIDADE)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .foto(UPDATED_FOTO)
            .sexo(UPDATED_SEXO);
        return dadosBasicos;
    }

    @BeforeEach
    public void initTest() {
        dadosBasicos = createEntity(em);
    }

    @Test
    @Transactional
    public void createDadosBasicos() throws Exception {
        int databaseSizeBeforeCreate = dadosBasicosRepository.findAll().size();

        // Create the DadosBasicos
        restDadosBasicosMockMvc.perform(post("/api/dados-basicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dadosBasicos)))
            .andExpect(status().isCreated());

        // Validate the DadosBasicos in the database
        List<DadosBasicos> dadosBasicosList = dadosBasicosRepository.findAll();
        assertThat(dadosBasicosList).hasSize(databaseSizeBeforeCreate + 1);
        DadosBasicos testDadosBasicos = dadosBasicosList.get(dadosBasicosList.size() - 1);
        assertThat(testDadosBasicos.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testDadosBasicos.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testDadosBasicos.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testDadosBasicos.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testDadosBasicos.getIdentidade()).isEqualTo(DEFAULT_IDENTIDADE);
        assertThat(testDadosBasicos.getPai()).isEqualTo(DEFAULT_PAI);
        assertThat(testDadosBasicos.getMae()).isEqualTo(DEFAULT_MAE);
        assertThat(testDadosBasicos.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testDadosBasicos.getSexo()).isEqualTo(DEFAULT_SEXO);
    }

    @Test
    @Transactional
    public void createDadosBasicosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dadosBasicosRepository.findAll().size();

        // Create the DadosBasicos with an existing ID
        dadosBasicos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDadosBasicosMockMvc.perform(post("/api/dados-basicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dadosBasicos)))
            .andExpect(status().isBadRequest());

        // Validate the DadosBasicos in the database
        List<DadosBasicos> dadosBasicosList = dadosBasicosRepository.findAll();
        assertThat(dadosBasicosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDadosBasicos() throws Exception {
        // Initialize the database
        dadosBasicosRepository.saveAndFlush(dadosBasicos);

        // Get all the dadosBasicosList
        restDadosBasicosMockMvc.perform(get("/api/dados-basicos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dadosBasicos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.intValue())))
            .andExpect(jsonPath("$.[*].identidade").value(hasItem(DEFAULT_IDENTIDADE.intValue())))
            .andExpect(jsonPath("$.[*].pai").value(hasItem(DEFAULT_PAI)))
            .andExpect(jsonPath("$.[*].mae").value(hasItem(DEFAULT_MAE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())));
    }
    
    @Test
    @Transactional
    public void getDadosBasicos() throws Exception {
        // Initialize the database
        dadosBasicosRepository.saveAndFlush(dadosBasicos);

        // Get the dadosBasicos
        restDadosBasicosMockMvc.perform(get("/api/dados-basicos/{id}", dadosBasicos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dadosBasicos.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.intValue()))
            .andExpect(jsonPath("$.identidade").value(DEFAULT_IDENTIDADE.intValue()))
            .andExpect(jsonPath("$.pai").value(DEFAULT_PAI))
            .andExpect(jsonPath("$.mae").value(DEFAULT_MAE))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDadosBasicos() throws Exception {
        // Get the dadosBasicos
        restDadosBasicosMockMvc.perform(get("/api/dados-basicos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDadosBasicos() throws Exception {
        // Initialize the database
        dadosBasicosRepository.saveAndFlush(dadosBasicos);

        int databaseSizeBeforeUpdate = dadosBasicosRepository.findAll().size();

        // Update the dadosBasicos
        DadosBasicos updatedDadosBasicos = dadosBasicosRepository.findById(dadosBasicos.getId()).get();
        // Disconnect from session so that the updates on updatedDadosBasicos are not directly saved in db
        em.detach(updatedDadosBasicos);
        updatedDadosBasicos
            .nome(UPDATED_NOME)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .data(UPDATED_DATA)
            .cpf(UPDATED_CPF)
            .identidade(UPDATED_IDENTIDADE)
            .pai(UPDATED_PAI)
            .mae(UPDATED_MAE)
            .foto(UPDATED_FOTO)
            .sexo(UPDATED_SEXO);

        restDadosBasicosMockMvc.perform(put("/api/dados-basicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDadosBasicos)))
            .andExpect(status().isOk());

        // Validate the DadosBasicos in the database
        List<DadosBasicos> dadosBasicosList = dadosBasicosRepository.findAll();
        assertThat(dadosBasicosList).hasSize(databaseSizeBeforeUpdate);
        DadosBasicos testDadosBasicos = dadosBasicosList.get(dadosBasicosList.size() - 1);
        assertThat(testDadosBasicos.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testDadosBasicos.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testDadosBasicos.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testDadosBasicos.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testDadosBasicos.getIdentidade()).isEqualTo(UPDATED_IDENTIDADE);
        assertThat(testDadosBasicos.getPai()).isEqualTo(UPDATED_PAI);
        assertThat(testDadosBasicos.getMae()).isEqualTo(UPDATED_MAE);
        assertThat(testDadosBasicos.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testDadosBasicos.getSexo()).isEqualTo(UPDATED_SEXO);
    }

    @Test
    @Transactional
    public void updateNonExistingDadosBasicos() throws Exception {
        int databaseSizeBeforeUpdate = dadosBasicosRepository.findAll().size();

        // Create the DadosBasicos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDadosBasicosMockMvc.perform(put("/api/dados-basicos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(dadosBasicos)))
            .andExpect(status().isBadRequest());

        // Validate the DadosBasicos in the database
        List<DadosBasicos> dadosBasicosList = dadosBasicosRepository.findAll();
        assertThat(dadosBasicosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDadosBasicos() throws Exception {
        // Initialize the database
        dadosBasicosRepository.saveAndFlush(dadosBasicos);

        int databaseSizeBeforeDelete = dadosBasicosRepository.findAll().size();

        // Delete the dadosBasicos
        restDadosBasicosMockMvc.perform(delete("/api/dados-basicos/{id}", dadosBasicos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DadosBasicos> dadosBasicosList = dadosBasicosRepository.findAll();
        assertThat(dadosBasicosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
