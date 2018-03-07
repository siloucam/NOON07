package com.outscape.noon07.web.rest;

import com.outscape.noon07.Noon07App;

import com.outscape.noon07.domain.Entrada;
import com.outscape.noon07.repository.EntradaRepository;
import com.outscape.noon07.service.EntradaService;
import com.outscape.noon07.web.rest.errors.ExceptionTranslator;
import com.outscape.noon07.service.dto.EntradaCriteria;
import com.outscape.noon07.service.EntradaQueryService;

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
import java.util.List;

import static com.outscape.noon07.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EntradaResource REST controller.
 *
 * @see EntradaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Noon07App.class)
public class EntradaResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    @Autowired
    private EntradaRepository entradaRepository;

    @Autowired
    private EntradaService entradaService;

    @Autowired
    private EntradaQueryService entradaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEntradaMockMvc;

    private Entrada entrada;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EntradaResource entradaResource = new EntradaResource(entradaService, entradaQueryService);
        this.restEntradaMockMvc = MockMvcBuilders.standaloneSetup(entradaResource)
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
    public static Entrada createEntity(EntityManager em) {
        Entrada entrada = new Entrada()
            .nome(DEFAULT_NOME)
            .valor(DEFAULT_VALOR);
        return entrada;
    }

    @Before
    public void initTest() {
        entrada = createEntity(em);
    }

    @Test
    @Transactional
    public void createEntrada() throws Exception {
        int databaseSizeBeforeCreate = entradaRepository.findAll().size();

        // Create the Entrada
        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrada)))
            .andExpect(status().isCreated());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeCreate + 1);
        Entrada testEntrada = entradaList.get(entradaList.size() - 1);
        assertThat(testEntrada.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testEntrada.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createEntradaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = entradaRepository.findAll().size();

        // Create the Entrada with an existing ID
        entrada.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntradaMockMvc.perform(post("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrada)))
            .andExpect(status().isBadRequest());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEntradas() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList
        restEntradaMockMvc.perform(get("/api/entradas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    @Test
    @Transactional
    public void getEntrada() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get the entrada
        restEntradaMockMvc.perform(get("/api/entradas/{id}", entrada.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(entrada.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllEntradasByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList where nome equals to DEFAULT_NOME
        defaultEntradaShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the entradaList where nome equals to UPDATED_NOME
        defaultEntradaShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEntradasByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultEntradaShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the entradaList where nome equals to UPDATED_NOME
        defaultEntradaShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllEntradasByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList where nome is not null
        defaultEntradaShouldBeFound("nome.specified=true");

        // Get all the entradaList where nome is null
        defaultEntradaShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllEntradasByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList where valor equals to DEFAULT_VALOR
        defaultEntradaShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the entradaList where valor equals to UPDATED_VALOR
        defaultEntradaShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEntradasByValorIsInShouldWork() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultEntradaShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the entradaList where valor equals to UPDATED_VALOR
        defaultEntradaShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllEntradasByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        entradaRepository.saveAndFlush(entrada);

        // Get all the entradaList where valor is not null
        defaultEntradaShouldBeFound("valor.specified=true");

        // Get all the entradaList where valor is null
        defaultEntradaShouldNotBeFound("valor.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEntradaShouldBeFound(String filter) throws Exception {
        restEntradaMockMvc.perform(get("/api/entradas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entrada.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEntradaShouldNotBeFound(String filter) throws Exception {
        restEntradaMockMvc.perform(get("/api/entradas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingEntrada() throws Exception {
        // Get the entrada
        restEntradaMockMvc.perform(get("/api/entradas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEntrada() throws Exception {
        // Initialize the database
        entradaService.save(entrada);

        int databaseSizeBeforeUpdate = entradaRepository.findAll().size();

        // Update the entrada
        Entrada updatedEntrada = entradaRepository.findOne(entrada.getId());
        // Disconnect from session so that the updates on updatedEntrada are not directly saved in db
        em.detach(updatedEntrada);
        updatedEntrada
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR);

        restEntradaMockMvc.perform(put("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEntrada)))
            .andExpect(status().isOk());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeUpdate);
        Entrada testEntrada = entradaList.get(entradaList.size() - 1);
        assertThat(testEntrada.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testEntrada.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingEntrada() throws Exception {
        int databaseSizeBeforeUpdate = entradaRepository.findAll().size();

        // Create the Entrada

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEntradaMockMvc.perform(put("/api/entradas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(entrada)))
            .andExpect(status().isCreated());

        // Validate the Entrada in the database
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEntrada() throws Exception {
        // Initialize the database
        entradaService.save(entrada);

        int databaseSizeBeforeDelete = entradaRepository.findAll().size();

        // Get the entrada
        restEntradaMockMvc.perform(delete("/api/entradas/{id}", entrada.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Entrada> entradaList = entradaRepository.findAll();
        assertThat(entradaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entrada.class);
        Entrada entrada1 = new Entrada();
        entrada1.setId(1L);
        Entrada entrada2 = new Entrada();
        entrada2.setId(entrada1.getId());
        assertThat(entrada1).isEqualTo(entrada2);
        entrada2.setId(2L);
        assertThat(entrada1).isNotEqualTo(entrada2);
        entrada1.setId(null);
        assertThat(entrada1).isNotEqualTo(entrada2);
    }
}
