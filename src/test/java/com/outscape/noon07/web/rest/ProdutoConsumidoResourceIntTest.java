package com.outscape.noon07.web.rest;

import com.outscape.noon07.Noon07App;

import com.outscape.noon07.domain.ProdutoConsumido;
import com.outscape.noon07.domain.Comanda;
import com.outscape.noon07.repository.ProdutoConsumidoRepository;
import com.outscape.noon07.service.ProdutoConsumidoService;
import com.outscape.noon07.web.rest.errors.ExceptionTranslator;
import com.outscape.noon07.service.dto.ProdutoConsumidoCriteria;
import com.outscape.noon07.service.ProdutoConsumidoQueryService;

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
 * Test class for the ProdutoConsumidoResource REST controller.
 *
 * @see ProdutoConsumidoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Noon07App.class)
public class ProdutoConsumidoResourceIntTest {

    private static final Integer DEFAULT_IDPRODUTO = 1;
    private static final Integer UPDATED_IDPRODUTO = 2;

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Float DEFAULT_VALOR = 1F;
    private static final Float UPDATED_VALOR = 2F;

    private static final Integer DEFAULT_QUANTIDADE = 1;
    private static final Integer UPDATED_QUANTIDADE = 2;

    private static final Integer DEFAULT_IDENTRADA = 1;
    private static final Integer UPDATED_IDENTRADA = 2;

    @Autowired
    private ProdutoConsumidoRepository produtoConsumidoRepository;

    @Autowired
    private ProdutoConsumidoService produtoConsumidoService;

    @Autowired
    private ProdutoConsumidoQueryService produtoConsumidoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProdutoConsumidoMockMvc;

    private ProdutoConsumido produtoConsumido;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProdutoConsumidoResource produtoConsumidoResource = new ProdutoConsumidoResource(produtoConsumidoService, produtoConsumidoQueryService);
        this.restProdutoConsumidoMockMvc = MockMvcBuilders.standaloneSetup(produtoConsumidoResource)
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
    public static ProdutoConsumido createEntity(EntityManager em) {
        ProdutoConsumido produtoConsumido = new ProdutoConsumido()
            .idproduto(DEFAULT_IDPRODUTO)
            .nome(DEFAULT_NOME)
            .valor(DEFAULT_VALOR)
            .quantidade(DEFAULT_QUANTIDADE)
            .identrada(DEFAULT_IDENTRADA);
        return produtoConsumido;
    }

    @Before
    public void initTest() {
        produtoConsumido = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdutoConsumido() throws Exception {
        int databaseSizeBeforeCreate = produtoConsumidoRepository.findAll().size();

        // Create the ProdutoConsumido
        restProdutoConsumidoMockMvc.perform(post("/api/produto-consumidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoConsumido)))
            .andExpect(status().isCreated());

        // Validate the ProdutoConsumido in the database
        List<ProdutoConsumido> produtoConsumidoList = produtoConsumidoRepository.findAll();
        assertThat(produtoConsumidoList).hasSize(databaseSizeBeforeCreate + 1);
        ProdutoConsumido testProdutoConsumido = produtoConsumidoList.get(produtoConsumidoList.size() - 1);
        assertThat(testProdutoConsumido.getIdproduto()).isEqualTo(DEFAULT_IDPRODUTO);
        assertThat(testProdutoConsumido.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testProdutoConsumido.getValor()).isEqualTo(DEFAULT_VALOR);
        assertThat(testProdutoConsumido.getQuantidade()).isEqualTo(DEFAULT_QUANTIDADE);
        assertThat(testProdutoConsumido.getIdentrada()).isEqualTo(DEFAULT_IDENTRADA);
    }

    @Test
    @Transactional
    public void createProdutoConsumidoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoConsumidoRepository.findAll().size();

        // Create the ProdutoConsumido with an existing ID
        produtoConsumido.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoConsumidoMockMvc.perform(post("/api/produto-consumidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoConsumido)))
            .andExpect(status().isBadRequest());

        // Validate the ProdutoConsumido in the database
        List<ProdutoConsumido> produtoConsumidoList = produtoConsumidoRepository.findAll();
        assertThat(produtoConsumidoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidos() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList
        restProdutoConsumidoMockMvc.perform(get("/api/produto-consumidos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtoConsumido.getId().intValue())))
            .andExpect(jsonPath("$.[*].idproduto").value(hasItem(DEFAULT_IDPRODUTO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].identrada").value(hasItem(DEFAULT_IDENTRADA)));
    }

    @Test
    @Transactional
    public void getProdutoConsumido() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get the produtoConsumido
        restProdutoConsumidoMockMvc.perform(get("/api/produto-consumidos/{id}", produtoConsumido.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produtoConsumido.getId().intValue()))
            .andExpect(jsonPath("$.idproduto").value(DEFAULT_IDPRODUTO))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.doubleValue()))
            .andExpect(jsonPath("$.quantidade").value(DEFAULT_QUANTIDADE))
            .andExpect(jsonPath("$.identrada").value(DEFAULT_IDENTRADA));
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdprodutoIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where idproduto equals to DEFAULT_IDPRODUTO
        defaultProdutoConsumidoShouldBeFound("idproduto.equals=" + DEFAULT_IDPRODUTO);

        // Get all the produtoConsumidoList where idproduto equals to UPDATED_IDPRODUTO
        defaultProdutoConsumidoShouldNotBeFound("idproduto.equals=" + UPDATED_IDPRODUTO);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdprodutoIsInShouldWork() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where idproduto in DEFAULT_IDPRODUTO or UPDATED_IDPRODUTO
        defaultProdutoConsumidoShouldBeFound("idproduto.in=" + DEFAULT_IDPRODUTO + "," + UPDATED_IDPRODUTO);

        // Get all the produtoConsumidoList where idproduto equals to UPDATED_IDPRODUTO
        defaultProdutoConsumidoShouldNotBeFound("idproduto.in=" + UPDATED_IDPRODUTO);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdprodutoIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where idproduto is not null
        defaultProdutoConsumidoShouldBeFound("idproduto.specified=true");

        // Get all the produtoConsumidoList where idproduto is null
        defaultProdutoConsumidoShouldNotBeFound("idproduto.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdprodutoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where idproduto greater than or equals to DEFAULT_IDPRODUTO
        defaultProdutoConsumidoShouldBeFound("idproduto.greaterOrEqualThan=" + DEFAULT_IDPRODUTO);

        // Get all the produtoConsumidoList where idproduto greater than or equals to UPDATED_IDPRODUTO
        defaultProdutoConsumidoShouldNotBeFound("idproduto.greaterOrEqualThan=" + UPDATED_IDPRODUTO);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdprodutoIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where idproduto less than or equals to DEFAULT_IDPRODUTO
        defaultProdutoConsumidoShouldNotBeFound("idproduto.lessThan=" + DEFAULT_IDPRODUTO);

        // Get all the produtoConsumidoList where idproduto less than or equals to UPDATED_IDPRODUTO
        defaultProdutoConsumidoShouldBeFound("idproduto.lessThan=" + UPDATED_IDPRODUTO);
    }


    @Test
    @Transactional
    public void getAllProdutoConsumidosByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where nome equals to DEFAULT_NOME
        defaultProdutoConsumidoShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the produtoConsumidoList where nome equals to UPDATED_NOME
        defaultProdutoConsumidoShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultProdutoConsumidoShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the produtoConsumidoList where nome equals to UPDATED_NOME
        defaultProdutoConsumidoShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where nome is not null
        defaultProdutoConsumidoShouldBeFound("nome.specified=true");

        // Get all the produtoConsumidoList where nome is null
        defaultProdutoConsumidoShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByValorIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where valor equals to DEFAULT_VALOR
        defaultProdutoConsumidoShouldBeFound("valor.equals=" + DEFAULT_VALOR);

        // Get all the produtoConsumidoList where valor equals to UPDATED_VALOR
        defaultProdutoConsumidoShouldNotBeFound("valor.equals=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByValorIsInShouldWork() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where valor in DEFAULT_VALOR or UPDATED_VALOR
        defaultProdutoConsumidoShouldBeFound("valor.in=" + DEFAULT_VALOR + "," + UPDATED_VALOR);

        // Get all the produtoConsumidoList where valor equals to UPDATED_VALOR
        defaultProdutoConsumidoShouldNotBeFound("valor.in=" + UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByValorIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where valor is not null
        defaultProdutoConsumidoShouldBeFound("valor.specified=true");

        // Get all the produtoConsumidoList where valor is null
        defaultProdutoConsumidoShouldNotBeFound("valor.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByQuantidadeIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where quantidade equals to DEFAULT_QUANTIDADE
        defaultProdutoConsumidoShouldBeFound("quantidade.equals=" + DEFAULT_QUANTIDADE);

        // Get all the produtoConsumidoList where quantidade equals to UPDATED_QUANTIDADE
        defaultProdutoConsumidoShouldNotBeFound("quantidade.equals=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByQuantidadeIsInShouldWork() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where quantidade in DEFAULT_QUANTIDADE or UPDATED_QUANTIDADE
        defaultProdutoConsumidoShouldBeFound("quantidade.in=" + DEFAULT_QUANTIDADE + "," + UPDATED_QUANTIDADE);

        // Get all the produtoConsumidoList where quantidade equals to UPDATED_QUANTIDADE
        defaultProdutoConsumidoShouldNotBeFound("quantidade.in=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByQuantidadeIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where quantidade is not null
        defaultProdutoConsumidoShouldBeFound("quantidade.specified=true");

        // Get all the produtoConsumidoList where quantidade is null
        defaultProdutoConsumidoShouldNotBeFound("quantidade.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByQuantidadeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where quantidade greater than or equals to DEFAULT_QUANTIDADE
        defaultProdutoConsumidoShouldBeFound("quantidade.greaterOrEqualThan=" + DEFAULT_QUANTIDADE);

        // Get all the produtoConsumidoList where quantidade greater than or equals to UPDATED_QUANTIDADE
        defaultProdutoConsumidoShouldNotBeFound("quantidade.greaterOrEqualThan=" + UPDATED_QUANTIDADE);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByQuantidadeIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where quantidade less than or equals to DEFAULT_QUANTIDADE
        defaultProdutoConsumidoShouldNotBeFound("quantidade.lessThan=" + DEFAULT_QUANTIDADE);

        // Get all the produtoConsumidoList where quantidade less than or equals to UPDATED_QUANTIDADE
        defaultProdutoConsumidoShouldBeFound("quantidade.lessThan=" + UPDATED_QUANTIDADE);
    }


    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdentradaIsEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where identrada equals to DEFAULT_IDENTRADA
        defaultProdutoConsumidoShouldBeFound("identrada.equals=" + DEFAULT_IDENTRADA);

        // Get all the produtoConsumidoList where identrada equals to UPDATED_IDENTRADA
        defaultProdutoConsumidoShouldNotBeFound("identrada.equals=" + UPDATED_IDENTRADA);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdentradaIsInShouldWork() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where identrada in DEFAULT_IDENTRADA or UPDATED_IDENTRADA
        defaultProdutoConsumidoShouldBeFound("identrada.in=" + DEFAULT_IDENTRADA + "," + UPDATED_IDENTRADA);

        // Get all the produtoConsumidoList where identrada equals to UPDATED_IDENTRADA
        defaultProdutoConsumidoShouldNotBeFound("identrada.in=" + UPDATED_IDENTRADA);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdentradaIsNullOrNotNull() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where identrada is not null
        defaultProdutoConsumidoShouldBeFound("identrada.specified=true");

        // Get all the produtoConsumidoList where identrada is null
        defaultProdutoConsumidoShouldNotBeFound("identrada.specified=false");
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdentradaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where identrada greater than or equals to DEFAULT_IDENTRADA
        defaultProdutoConsumidoShouldBeFound("identrada.greaterOrEqualThan=" + DEFAULT_IDENTRADA);

        // Get all the produtoConsumidoList where identrada greater than or equals to UPDATED_IDENTRADA
        defaultProdutoConsumidoShouldNotBeFound("identrada.greaterOrEqualThan=" + UPDATED_IDENTRADA);
    }

    @Test
    @Transactional
    public void getAllProdutoConsumidosByIdentradaIsLessThanSomething() throws Exception {
        // Initialize the database
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);

        // Get all the produtoConsumidoList where identrada less than or equals to DEFAULT_IDENTRADA
        defaultProdutoConsumidoShouldNotBeFound("identrada.lessThan=" + DEFAULT_IDENTRADA);

        // Get all the produtoConsumidoList where identrada less than or equals to UPDATED_IDENTRADA
        defaultProdutoConsumidoShouldBeFound("identrada.lessThan=" + UPDATED_IDENTRADA);
    }


    @Test
    @Transactional
    public void getAllProdutoConsumidosByComandaIsEqualToSomething() throws Exception {
        // Initialize the database
        Comanda comanda = ComandaResourceIntTest.createEntity(em);
        em.persist(comanda);
        em.flush();
        produtoConsumido.setComanda(comanda);
        produtoConsumidoRepository.saveAndFlush(produtoConsumido);
        Long comandaId = comanda.getId();

        // Get all the produtoConsumidoList where comanda equals to comandaId
        defaultProdutoConsumidoShouldBeFound("comandaId.equals=" + comandaId);

        // Get all the produtoConsumidoList where comanda equals to comandaId + 1
        defaultProdutoConsumidoShouldNotBeFound("comandaId.equals=" + (comandaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProdutoConsumidoShouldBeFound(String filter) throws Exception {
        restProdutoConsumidoMockMvc.perform(get("/api/produto-consumidos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtoConsumido.getId().intValue())))
            .andExpect(jsonPath("$.[*].idproduto").value(hasItem(DEFAULT_IDPRODUTO)))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.doubleValue())))
            .andExpect(jsonPath("$.[*].quantidade").value(hasItem(DEFAULT_QUANTIDADE)))
            .andExpect(jsonPath("$.[*].identrada").value(hasItem(DEFAULT_IDENTRADA)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProdutoConsumidoShouldNotBeFound(String filter) throws Exception {
        restProdutoConsumidoMockMvc.perform(get("/api/produto-consumidos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingProdutoConsumido() throws Exception {
        // Get the produtoConsumido
        restProdutoConsumidoMockMvc.perform(get("/api/produto-consumidos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdutoConsumido() throws Exception {
        // Initialize the database
        produtoConsumidoService.save(produtoConsumido);

        int databaseSizeBeforeUpdate = produtoConsumidoRepository.findAll().size();

        // Update the produtoConsumido
        ProdutoConsumido updatedProdutoConsumido = produtoConsumidoRepository.findOne(produtoConsumido.getId());
        // Disconnect from session so that the updates on updatedProdutoConsumido are not directly saved in db
        em.detach(updatedProdutoConsumido);
        updatedProdutoConsumido
            .idproduto(UPDATED_IDPRODUTO)
            .nome(UPDATED_NOME)
            .valor(UPDATED_VALOR)
            .quantidade(UPDATED_QUANTIDADE)
            .identrada(UPDATED_IDENTRADA);

        restProdutoConsumidoMockMvc.perform(put("/api/produto-consumidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdutoConsumido)))
            .andExpect(status().isOk());

        // Validate the ProdutoConsumido in the database
        List<ProdutoConsumido> produtoConsumidoList = produtoConsumidoRepository.findAll();
        assertThat(produtoConsumidoList).hasSize(databaseSizeBeforeUpdate);
        ProdutoConsumido testProdutoConsumido = produtoConsumidoList.get(produtoConsumidoList.size() - 1);
        assertThat(testProdutoConsumido.getIdproduto()).isEqualTo(UPDATED_IDPRODUTO);
        assertThat(testProdutoConsumido.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testProdutoConsumido.getValor()).isEqualTo(UPDATED_VALOR);
        assertThat(testProdutoConsumido.getQuantidade()).isEqualTo(UPDATED_QUANTIDADE);
        assertThat(testProdutoConsumido.getIdentrada()).isEqualTo(UPDATED_IDENTRADA);
    }

    @Test
    @Transactional
    public void updateNonExistingProdutoConsumido() throws Exception {
        int databaseSizeBeforeUpdate = produtoConsumidoRepository.findAll().size();

        // Create the ProdutoConsumido

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProdutoConsumidoMockMvc.perform(put("/api/produto-consumidos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoConsumido)))
            .andExpect(status().isCreated());

        // Validate the ProdutoConsumido in the database
        List<ProdutoConsumido> produtoConsumidoList = produtoConsumidoRepository.findAll();
        assertThat(produtoConsumidoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProdutoConsumido() throws Exception {
        // Initialize the database
        produtoConsumidoService.save(produtoConsumido);

        int databaseSizeBeforeDelete = produtoConsumidoRepository.findAll().size();

        // Get the produtoConsumido
        restProdutoConsumidoMockMvc.perform(delete("/api/produto-consumidos/{id}", produtoConsumido.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProdutoConsumido> produtoConsumidoList = produtoConsumidoRepository.findAll();
        assertThat(produtoConsumidoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutoConsumido.class);
        ProdutoConsumido produtoConsumido1 = new ProdutoConsumido();
        produtoConsumido1.setId(1L);
        ProdutoConsumido produtoConsumido2 = new ProdutoConsumido();
        produtoConsumido2.setId(produtoConsumido1.getId());
        assertThat(produtoConsumido1).isEqualTo(produtoConsumido2);
        produtoConsumido2.setId(2L);
        assertThat(produtoConsumido1).isNotEqualTo(produtoConsumido2);
        produtoConsumido1.setId(null);
        assertThat(produtoConsumido1).isNotEqualTo(produtoConsumido2);
    }
}
