package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.Saldo;
import com.clinic.repository.SaldoRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SaldoResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SaldoResourceIT {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final Instant DEFAULT_DATA_ATUALIZACAO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA_ATUALIZACAO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private SaldoRepository saldoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSaldoMockMvc;

    private Saldo saldo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Saldo createEntity(EntityManager em) {
        Saldo saldo = new Saldo()
            .valor(DEFAULT_VALOR)
            .dataAtualizacao(DEFAULT_DATA_ATUALIZACAO);
        return saldo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Saldo createUpdatedEntity(EntityManager em) {
        Saldo saldo = new Saldo()
            .valor(UPDATED_VALOR)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);
        return saldo;
    }

    @BeforeEach
    public void initTest() {
        saldo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaldo() throws Exception {
        int databaseSizeBeforeCreate = saldoRepository.findAll().size();

        // Create the Saldo
        restSaldoMockMvc.perform(post("/api/saldos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saldo)))
            .andExpect(status().isCreated());

        // Validate the Saldo in the database
        List<Saldo> saldoList = saldoRepository.findAll();
        assertThat(saldoList).hasSize(databaseSizeBeforeCreate + 1);
        Saldo testSaldo = saldoList.get(saldoList.size() - 1);
        assertThat(testSaldo.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testSaldo.getDataAtualizacao()).isEqualTo(DEFAULT_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    public void createSaldoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saldoRepository.findAll().size();

        // Create the Saldo with an existing ID
        saldo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaldoMockMvc.perform(post("/api/saldos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saldo)))
            .andExpect(status().isBadRequest());

        // Validate the Saldo in the database
        List<Saldo> saldoList = saldoRepository.findAll();
        assertThat(saldoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = saldoRepository.findAll().size();
        // set the field null
        saldo.setValor(null);

        // Create the Saldo, which fails.

        restSaldoMockMvc.perform(post("/api/saldos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saldo)))
            .andExpect(status().isBadRequest());

        List<Saldo> saldoList = saldoRepository.findAll();
        assertThat(saldoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaldos() throws Exception {
        // Initialize the database
        saldoRepository.saveAndFlush(saldo);

        // Get all the saldoList
        restSaldoMockMvc.perform(get("/api/saldos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saldo.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].dataAtualizacao").value(hasItem(DEFAULT_DATA_ATUALIZACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getSaldo() throws Exception {
        // Initialize the database
        saldoRepository.saveAndFlush(saldo);

        // Get the saldo
        restSaldoMockMvc.perform(get("/api/saldos/{id}", saldo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(saldo.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.dataAtualizacao").value(DEFAULT_DATA_ATUALIZACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSaldo() throws Exception {
        // Get the saldo
        restSaldoMockMvc.perform(get("/api/saldos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaldo() throws Exception {
        // Initialize the database
        saldoRepository.saveAndFlush(saldo);

        int databaseSizeBeforeUpdate = saldoRepository.findAll().size();

        // Update the saldo
        Saldo updatedSaldo = saldoRepository.findById(saldo.getId()).get();
        // Disconnect from session so that the updates on updatedSaldo are not directly saved in db
        em.detach(updatedSaldo);
        updatedSaldo
            .valor(UPDATED_VALOR)
            .dataAtualizacao(UPDATED_DATA_ATUALIZACAO);

        restSaldoMockMvc.perform(put("/api/saldos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSaldo)))
            .andExpect(status().isOk());

        // Validate the Saldo in the database
        List<Saldo> saldoList = saldoRepository.findAll();
        assertThat(saldoList).hasSize(databaseSizeBeforeUpdate);
        Saldo testSaldo = saldoList.get(saldoList.size() - 1);
        assertThat(testSaldo.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testSaldo.getDataAtualizacao()).isEqualTo(UPDATED_DATA_ATUALIZACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingSaldo() throws Exception {
        int databaseSizeBeforeUpdate = saldoRepository.findAll().size();

        // Create the Saldo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSaldoMockMvc.perform(put("/api/saldos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(saldo)))
            .andExpect(status().isBadRequest());

        // Validate the Saldo in the database
        List<Saldo> saldoList = saldoRepository.findAll();
        assertThat(saldoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSaldo() throws Exception {
        // Initialize the database
        saldoRepository.saveAndFlush(saldo);

        int databaseSizeBeforeDelete = saldoRepository.findAll().size();

        // Delete the saldo
        restSaldoMockMvc.perform(delete("/api/saldos/{id}", saldo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Saldo> saldoList = saldoRepository.findAll();
        assertThat(saldoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
