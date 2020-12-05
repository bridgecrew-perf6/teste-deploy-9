package com.clinic.web.rest;

import com.clinic.ClinicApp;
import com.clinic.domain.Movimentacao;
import com.clinic.repository.MovimentacaoRepository;

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

import com.clinic.domain.enumeration.TipoMovimento;
/**
 * Integration tests for the {@link MovimentacaoResource} REST controller.
 */
@SpringBootTest(classes = ClinicApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MovimentacaoResourceIT {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    private static final Instant DEFAULT_DATA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DESCRICAO = "AAAAAAAAAA";
    private static final String UPDATED_DESCRICAO = "BBBBBBBBBB";

    private static final TipoMovimento DEFAULT_TIPO_MOVIMENTO = TipoMovimento.ENTRADA;
    private static final TipoMovimento UPDATED_TIPO_MOVIMENTO = TipoMovimento.SAIDA;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMovimentacaoMockMvc;

    private Movimentacao movimentacao;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movimentacao createEntity(EntityManager em) {
        Movimentacao movimentacao = new Movimentacao()
            .valor(DEFAULT_VALOR)
            .data(DEFAULT_DATA)
            .descricao(DEFAULT_DESCRICAO)
            .tipoMovimento(DEFAULT_TIPO_MOVIMENTO);
        return movimentacao;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Movimentacao createUpdatedEntity(EntityManager em) {
        Movimentacao movimentacao = new Movimentacao()
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .tipoMovimento(UPDATED_TIPO_MOVIMENTO);
        return movimentacao;
    }

    @BeforeEach
    public void initTest() {
        movimentacao = createEntity(em);
    }

    @Test
    @Transactional
    public void createMovimentacao() throws Exception {
        int databaseSizeBeforeCreate = movimentacaoRepository.findAll().size();

        // Create the Movimentacao
        restMovimentacaoMockMvc.perform(post("/api/movimentacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movimentacao)))
            .andExpect(status().isCreated());

        // Validate the Movimentacao in the database
        List<Movimentacao> movimentacaoList = movimentacaoRepository.findAll();
        assertThat(movimentacaoList).hasSize(databaseSizeBeforeCreate + 1);
        Movimentacao testMovimentacao = movimentacaoList.get(movimentacaoList.size() - 1);
        assertThat(testMovimentacao.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testMovimentacao.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testMovimentacao.getDescricao()).isEqualTo(DEFAULT_DESCRICAO);
        assertThat(testMovimentacao.getTipoMovimento()).isEqualTo(DEFAULT_TIPO_MOVIMENTO);
    }

    @Test
    @Transactional
    public void createMovimentacaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = movimentacaoRepository.findAll().size();

        // Create the Movimentacao with an existing ID
        movimentacao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMovimentacaoMockMvc.perform(post("/api/movimentacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movimentacao)))
            .andExpect(status().isBadRequest());

        // Validate the Movimentacao in the database
        List<Movimentacao> movimentacaoList = movimentacaoRepository.findAll();
        assertThat(movimentacaoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValorIsRequired() throws Exception {
        int databaseSizeBeforeTest = movimentacaoRepository.findAll().size();
        // set the field null
        movimentacao.setValor(null);

        // Create the Movimentacao, which fails.

        restMovimentacaoMockMvc.perform(post("/api/movimentacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movimentacao)))
            .andExpect(status().isBadRequest());

        List<Movimentacao> movimentacaoList = movimentacaoRepository.findAll();
        assertThat(movimentacaoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMovimentacaos() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        // Get all the movimentacaoList
        restMovimentacaoMockMvc.perform(get("/api/movimentacaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(movimentacao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].descricao").value(hasItem(DEFAULT_DESCRICAO)))
            .andExpect(jsonPath("$.[*].tipoMovimento").value(hasItem(DEFAULT_TIPO_MOVIMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getMovimentacao() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        // Get the movimentacao
        restMovimentacaoMockMvc.perform(get("/api/movimentacaos/{id}", movimentacao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(movimentacao.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.descricao").value(DEFAULT_DESCRICAO))
            .andExpect(jsonPath("$.tipoMovimento").value(DEFAULT_TIPO_MOVIMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMovimentacao() throws Exception {
        // Get the movimentacao
        restMovimentacaoMockMvc.perform(get("/api/movimentacaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMovimentacao() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        int databaseSizeBeforeUpdate = movimentacaoRepository.findAll().size();

        // Update the movimentacao
        Movimentacao updatedMovimentacao = movimentacaoRepository.findById(movimentacao.getId()).get();
        // Disconnect from session so that the updates on updatedMovimentacao are not directly saved in db
        em.detach(updatedMovimentacao);
        updatedMovimentacao
            .valor(UPDATED_VALOR)
            .data(UPDATED_DATA)
            .descricao(UPDATED_DESCRICAO)
            .tipoMovimento(UPDATED_TIPO_MOVIMENTO);

        restMovimentacaoMockMvc.perform(put("/api/movimentacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMovimentacao)))
            .andExpect(status().isOk());

        // Validate the Movimentacao in the database
        List<Movimentacao> movimentacaoList = movimentacaoRepository.findAll();
        assertThat(movimentacaoList).hasSize(databaseSizeBeforeUpdate);
        Movimentacao testMovimentacao = movimentacaoList.get(movimentacaoList.size() - 1);
        assertThat(testMovimentacao.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testMovimentacao.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testMovimentacao.getDescricao()).isEqualTo(UPDATED_DESCRICAO);
        assertThat(testMovimentacao.getTipoMovimento()).isEqualTo(UPDATED_TIPO_MOVIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingMovimentacao() throws Exception {
        int databaseSizeBeforeUpdate = movimentacaoRepository.findAll().size();

        // Create the Movimentacao

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMovimentacaoMockMvc.perform(put("/api/movimentacaos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(movimentacao)))
            .andExpect(status().isBadRequest());

        // Validate the Movimentacao in the database
        List<Movimentacao> movimentacaoList = movimentacaoRepository.findAll();
        assertThat(movimentacaoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMovimentacao() throws Exception {
        // Initialize the database
        movimentacaoRepository.saveAndFlush(movimentacao);

        int databaseSizeBeforeDelete = movimentacaoRepository.findAll().size();

        // Delete the movimentacao
        restMovimentacaoMockMvc.perform(delete("/api/movimentacaos/{id}", movimentacao.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Movimentacao> movimentacaoList = movimentacaoRepository.findAll();
        assertThat(movimentacaoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
