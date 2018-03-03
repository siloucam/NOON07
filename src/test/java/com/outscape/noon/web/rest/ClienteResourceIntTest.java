package com.outscape.noon.web.rest;

import com.outscape.noon.Noon07App;

import com.outscape.noon.domain.Cliente;
import com.outscape.noon.domain.Comanda;
import com.outscape.noon.repository.ClienteRepository;
import com.outscape.noon.service.ClienteService;
import com.outscape.noon.repository.search.ClienteSearchRepository;
import com.outscape.noon.web.rest.errors.ExceptionTranslator;
import com.outscape.noon.service.dto.ClienteCriteria;
import com.outscape.noon.service.ClienteQueryService;

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

import static com.outscape.noon.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ClienteResource REST controller.
 *
 * @see ClienteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Noon07App.class)
public class ClienteResourceIntTest {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_DOCUMENTO = "AAAAAAAAAA";
    private static final String UPDATED_DOCUMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteSearchRepository clienteSearchRepository;

    @Autowired
    private ClienteQueryService clienteQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteService, clienteQueryService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nome(DEFAULT_NOME)
            .documento(DEFAULT_DOCUMENTO)
            .telefone(DEFAULT_TELEFONE);
        return cliente;
    }

    @Before
    public void initTest() {
        clienteSearchRepository.deleteAll();
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testCliente.getDocumento()).isEqualTo(DEFAULT_DOCUMENTO);
        assertThat(testCliente.getTelefone()).isEqualTo(DEFAULT_TELEFONE);

        // Validate the Cliente in Elasticsearch
        Cliente clienteEs = clienteSearchRepository.findOne(testCliente.getId());
        assertThat(clienteEs).isEqualToIgnoringGivenFields(testCliente);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }

    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.documento").value(DEFAULT_DOCUMENTO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()));
    }

    @Test
    @Transactional
    public void getAllClientesByNomeIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nome equals to DEFAULT_NOME
        defaultClienteShouldBeFound("nome.equals=" + DEFAULT_NOME);

        // Get all the clienteList where nome equals to UPDATED_NOME
        defaultClienteShouldNotBeFound("nome.equals=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllClientesByNomeIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nome in DEFAULT_NOME or UPDATED_NOME
        defaultClienteShouldBeFound("nome.in=" + DEFAULT_NOME + "," + UPDATED_NOME);

        // Get all the clienteList where nome equals to UPDATED_NOME
        defaultClienteShouldNotBeFound("nome.in=" + UPDATED_NOME);
    }

    @Test
    @Transactional
    public void getAllClientesByNomeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nome is not null
        defaultClienteShouldBeFound("nome.specified=true");

        // Get all the clienteList where nome is null
        defaultClienteShouldNotBeFound("nome.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByDocumentoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where documento equals to DEFAULT_DOCUMENTO
        defaultClienteShouldBeFound("documento.equals=" + DEFAULT_DOCUMENTO);

        // Get all the clienteList where documento equals to UPDATED_DOCUMENTO
        defaultClienteShouldNotBeFound("documento.equals=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllClientesByDocumentoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where documento in DEFAULT_DOCUMENTO or UPDATED_DOCUMENTO
        defaultClienteShouldBeFound("documento.in=" + DEFAULT_DOCUMENTO + "," + UPDATED_DOCUMENTO);

        // Get all the clienteList where documento equals to UPDATED_DOCUMENTO
        defaultClienteShouldNotBeFound("documento.in=" + UPDATED_DOCUMENTO);
    }

    @Test
    @Transactional
    public void getAllClientesByDocumentoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where documento is not null
        defaultClienteShouldBeFound("documento.specified=true");

        // Get all the clienteList where documento is null
        defaultClienteShouldNotBeFound("documento.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByTelefoneIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone equals to DEFAULT_TELEFONE
        defaultClienteShouldBeFound("telefone.equals=" + DEFAULT_TELEFONE);

        // Get all the clienteList where telefone equals to UPDATED_TELEFONE
        defaultClienteShouldNotBeFound("telefone.equals=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllClientesByTelefoneIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone in DEFAULT_TELEFONE or UPDATED_TELEFONE
        defaultClienteShouldBeFound("telefone.in=" + DEFAULT_TELEFONE + "," + UPDATED_TELEFONE);

        // Get all the clienteList where telefone equals to UPDATED_TELEFONE
        defaultClienteShouldNotBeFound("telefone.in=" + UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    public void getAllClientesByTelefoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where telefone is not null
        defaultClienteShouldBeFound("telefone.specified=true");

        // Get all the clienteList where telefone is null
        defaultClienteShouldNotBeFound("telefone.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByComandaIsEqualToSomething() throws Exception {
        // Initialize the database
        Comanda comanda = ComandaResourceIntTest.createEntity(em);
        em.persist(comanda);
        em.flush();
        cliente.addComanda(comanda);
        clienteRepository.saveAndFlush(cliente);
        Long comandaId = comanda.getId();

        // Get all the clienteList where comanda equals to comandaId
        defaultClienteShouldBeFound("comandaId.equals=" + comandaId);

        // Get all the clienteList where comanda equals to comandaId + 1
        defaultClienteShouldNotBeFound("comandaId.equals=" + (comandaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultClienteShouldBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultClienteShouldNotBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findOne(cliente.getId());
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .nome(UPDATED_NOME)
            .documento(UPDATED_DOCUMENTO)
            .telefone(UPDATED_TELEFONE);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCliente)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testCliente.getDocumento()).isEqualTo(UPDATED_DOCUMENTO);
        assertThat(testCliente.getTelefone()).isEqualTo(UPDATED_TELEFONE);

        // Validate the Cliente in Elasticsearch
        Cliente clienteEs = clienteSearchRepository.findOne(testCliente.getId());
        assertThat(clienteEs).isEqualToIgnoringGivenFields(testCliente);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cliente)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Get the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean clienteExistsInEs = clienteSearchRepository.exists(cliente.getId());
        assertThat(clienteExistsInEs).isFalse();

        // Validate the database is empty
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchCliente() throws Exception {
        // Initialize the database
        clienteService.save(cliente);

        // Search the cliente
        restClienteMockMvc.perform(get("/api/_search/clientes?query=id:" + cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].documento").value(hasItem(DEFAULT_DOCUMENTO.toString())))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setId(2L);
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setId(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }
}
