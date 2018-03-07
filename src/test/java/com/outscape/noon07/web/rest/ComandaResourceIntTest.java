package com.outscape.noon07.web.rest;

import com.outscape.noon07.Noon07App;

import com.outscape.noon07.domain.Comanda;
import com.outscape.noon07.domain.ProdutoConsumido;
import com.outscape.noon07.domain.Cliente;
import com.outscape.noon07.repository.ComandaRepository;
import com.outscape.noon07.service.ComandaService;
import com.outscape.noon07.web.rest.errors.ExceptionTranslator;
import com.outscape.noon07.service.dto.ComandaCriteria;
import com.outscape.noon07.service.ComandaQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.outscape.noon07.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.outscape.noon07.domain.enumeration.StatusComanda;
/**
 * Test class for the ComandaResource REST controller.
 *
 * @see ComandaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Noon07App.class)
public class ComandaResourceIntTest {

    private static final Integer DEFAULT_NUMERO = 1;
    private static final Integer UPDATED_NUMERO = 2;

    private static final LocalDate DEFAULT_DATA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_TOTAL = 1F;
    private static final Float UPDATED_TOTAL = 2F;

    private static final StatusComanda DEFAULT_STATUS = StatusComanda.ABERTA;
    private static final StatusComanda UPDATED_STATUS = StatusComanda.FECHADA;

    @Autowired
    private ComandaRepository comandaRepository;

    @Autowired
    private ComandaService comandaService;

    @Autowired
    private ComandaQueryService comandaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restComandaMockMvc;

    private Comanda comanda;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ComandaResource comandaResource = new ComandaResource(comandaService, comandaQueryService);
        this.restComandaMockMvc = MockMvcBuilders.standaloneSetup(comandaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comanda createEntity(EntityManager em) {
        Comanda comanda = new Comanda()
            .numero(DEFAULT_NUMERO)
            .data(DEFAULT_DATA)
            .total(DEFAULT_TOTAL)
            .status(DEFAULT_STATUS);
        return comanda;
    }

    @Before
    public void initTest() {
        comanda = createEntity(em);
    }

    @Test
    @Transactional
    public void createComanda() throws Exception {
        int databaseSizeBeforeCreate = comandaRepository.findAll().size();

        // Create the Comanda
        restComandaMockMvc.perform(post("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isCreated());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeCreate + 1);
        Comanda testComanda = comandaList.get(comandaList.size() - 1);
        assertThat(testComanda.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testComanda.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testComanda.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testComanda.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createComandaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = comandaRepository.findAll().size();

        // Create the Comanda with an existing ID
        comanda.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restComandaMockMvc.perform(post("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isBadRequest());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = comandaRepository.findAll().size();
        // set the field null
        comanda.setNumero(null);

        // Create the Comanda, which fails.

        restComandaMockMvc.perform(post("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isBadRequest());

        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = comandaRepository.findAll().size();
        // set the field null
        comanda.setData(null);

        // Create the Comanda, which fails.

        restComandaMockMvc.perform(post("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isBadRequest());

        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllComandas() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList
        restComandaMockMvc.perform(get("/api/comandas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comanda.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getComanda() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get the comanda
        restComandaMockMvc.perform(get("/api/comandas/{id}", comanda.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comanda.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllComandasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where numero equals to DEFAULT_NUMERO
        defaultComandaShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the comandaList where numero equals to UPDATED_NUMERO
        defaultComandaShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllComandasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultComandaShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the comandaList where numero equals to UPDATED_NUMERO
        defaultComandaShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllComandasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where numero is not null
        defaultComandaShouldBeFound("numero.specified=true");

        // Get all the comandaList where numero is null
        defaultComandaShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllComandasByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where numero greater than or equals to DEFAULT_NUMERO
        defaultComandaShouldBeFound("numero.greaterOrEqualThan=" + DEFAULT_NUMERO);

        // Get all the comandaList where numero greater than or equals to UPDATED_NUMERO
        defaultComandaShouldNotBeFound("numero.greaterOrEqualThan=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllComandasByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where numero less than or equals to DEFAULT_NUMERO
        defaultComandaShouldNotBeFound("numero.lessThan=" + DEFAULT_NUMERO);

        // Get all the comandaList where numero less than or equals to UPDATED_NUMERO
        defaultComandaShouldBeFound("numero.lessThan=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllComandasByDataIsEqualToSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where data equals to DEFAULT_DATA
        defaultComandaShouldBeFound("data.equals=" + DEFAULT_DATA);

        // Get all the comandaList where data equals to UPDATED_DATA
        defaultComandaShouldNotBeFound("data.equals=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComandasByDataIsInShouldWork() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where data in DEFAULT_DATA or UPDATED_DATA
        defaultComandaShouldBeFound("data.in=" + DEFAULT_DATA + "," + UPDATED_DATA);

        // Get all the comandaList where data equals to UPDATED_DATA
        defaultComandaShouldNotBeFound("data.in=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComandasByDataIsNullOrNotNull() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where data is not null
        defaultComandaShouldBeFound("data.specified=true");

        // Get all the comandaList where data is null
        defaultComandaShouldNotBeFound("data.specified=false");
    }

    @Test
    @Transactional
    public void getAllComandasByDataIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where data greater than or equals to DEFAULT_DATA
        defaultComandaShouldBeFound("data.greaterOrEqualThan=" + DEFAULT_DATA);

        // Get all the comandaList where data greater than or equals to UPDATED_DATA
        defaultComandaShouldNotBeFound("data.greaterOrEqualThan=" + UPDATED_DATA);
    }

    @Test
    @Transactional
    public void getAllComandasByDataIsLessThanSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where data less than or equals to DEFAULT_DATA
        defaultComandaShouldNotBeFound("data.lessThan=" + DEFAULT_DATA);

        // Get all the comandaList where data less than or equals to UPDATED_DATA
        defaultComandaShouldBeFound("data.lessThan=" + UPDATED_DATA);
    }


    @Test
    @Transactional
    public void getAllComandasByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where total equals to DEFAULT_TOTAL
        defaultComandaShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the comandaList where total equals to UPDATED_TOTAL
        defaultComandaShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllComandasByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultComandaShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the comandaList where total equals to UPDATED_TOTAL
        defaultComandaShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllComandasByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where total is not null
        defaultComandaShouldBeFound("total.specified=true");

        // Get all the comandaList where total is null
        defaultComandaShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllComandasByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where status equals to DEFAULT_STATUS
        defaultComandaShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the comandaList where status equals to UPDATED_STATUS
        defaultComandaShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllComandasByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultComandaShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the comandaList where status equals to UPDATED_STATUS
        defaultComandaShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllComandasByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        comandaRepository.saveAndFlush(comanda);

        // Get all the comandaList where status is not null
        defaultComandaShouldBeFound("status.specified=true");

        // Get all the comandaList where status is null
        defaultComandaShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllComandasByProdutoIsEqualToSomething() throws Exception {
        // Initialize the database
        ProdutoConsumido produto = ProdutoConsumidoResourceIntTest.createEntity(em);
        em.persist(produto);
        em.flush();
        comanda.addProduto(produto);
        comandaRepository.saveAndFlush(comanda);
        Long produtoId = produto.getId();

        // Get all the comandaList where produto equals to produtoId
        defaultComandaShouldBeFound("produtoId.equals=" + produtoId);

        // Get all the comandaList where produto equals to produtoId + 1
        defaultComandaShouldNotBeFound("produtoId.equals=" + (produtoId + 1));
    }


    @Test
    @Transactional
    public void getAllComandasByClienteIsEqualToSomething() throws Exception {
        // Initialize the database
        Cliente cliente = ClienteResourceIntTest.createEntity(em);
        em.persist(cliente);
        em.flush();
        comanda.setCliente(cliente);
        comandaRepository.saveAndFlush(comanda);
        Long clienteId = cliente.getId();

        // Get all the comandaList where cliente equals to clienteId
        defaultComandaShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the comandaList where cliente equals to clienteId + 1
        defaultComandaShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultComandaShouldBeFound(String filter) throws Exception {
        restComandaMockMvc.perform(get("/api/comandas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comanda.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultComandaShouldNotBeFound(String filter) throws Exception {
        restComandaMockMvc.perform(get("/api/comandas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingComanda() throws Exception {
        // Get the comanda
        restComandaMockMvc.perform(get("/api/comandas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComanda() throws Exception {
        // Initialize the database
        comandaService.save(comanda);

        int databaseSizeBeforeUpdate = comandaRepository.findAll().size();

        // Update the comanda
        Comanda updatedComanda = comandaRepository.findOne(comanda.getId());
        // Disconnect from session so that the updates on updatedComanda are not directly saved in db
        em.detach(updatedComanda);
        updatedComanda
            .numero(UPDATED_NUMERO)
            .data(UPDATED_DATA)
            .total(UPDATED_TOTAL)
            .status(UPDATED_STATUS);

        restComandaMockMvc.perform(put("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedComanda)))
            .andExpect(status().isOk());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeUpdate);
        Comanda testComanda = comandaList.get(comandaList.size() - 1);
        assertThat(testComanda.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testComanda.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testComanda.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testComanda.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingComanda() throws Exception {
        int databaseSizeBeforeUpdate = comandaRepository.findAll().size();

        // Create the Comanda

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restComandaMockMvc.perform(put("/api/comandas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(comanda)))
            .andExpect(status().isCreated());

        // Validate the Comanda in the database
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteComanda() throws Exception {
        // Initialize the database
        comandaService.save(comanda);

        int databaseSizeBeforeDelete = comandaRepository.findAll().size();

        // Get the comanda
        restComandaMockMvc.perform(delete("/api/comandas/{id}", comanda.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Comanda> comandaList = comandaRepository.findAll();
        assertThat(comandaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Comanda.class);
        Comanda comanda1 = new Comanda();
        comanda1.setId(1L);
        Comanda comanda2 = new Comanda();
        comanda2.setId(comanda1.getId());
        assertThat(comanda1).isEqualTo(comanda2);
        comanda2.setId(2L);
        assertThat(comanda1).isNotEqualTo(comanda2);
        comanda1.setId(null);
        assertThat(comanda1).isNotEqualTo(comanda2);
    }
}
