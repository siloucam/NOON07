package com.outscape.noon.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.outscape.noon.domain.Cliente;
import com.outscape.noon.service.ClienteService;
import com.outscape.noon.web.rest.errors.BadRequestAlertException;
import com.outscape.noon.web.rest.util.HeaderUtil;
import com.outscape.noon.web.rest.util.PaginationUtil;
import com.outscape.noon.service.dto.ClienteCriteria;
import com.outscape.noon.service.ClienteQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Cliente.
 */
@RestController
@RequestMapping("/api")
public class ClienteResource {

    private final Logger log = LoggerFactory.getLogger(ClienteResource.class);

    private static final String ENTITY_NAME = "cliente";

    private final ClienteService clienteService;

    private final ClienteQueryService clienteQueryService;

    public ClienteResource(ClienteService clienteService, ClienteQueryService clienteQueryService) {
        this.clienteService = clienteService;
        this.clienteQueryService = clienteQueryService;
    }

    /**
     * POST  /clientes : Create a new cliente.
     *
     * @param cliente the cliente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cliente, or with status 400 (Bad Request) if the cliente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/clientes")
    @Timed
    public ResponseEntity<Cliente> createCliente(@RequestBody Cliente cliente) throws URISyntaxException {
        log.debug("REST request to save Cliente : {}", cliente);
        if (cliente.getId() != null) {
            throw new BadRequestAlertException("A new cliente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cliente result = clienteService.save(cliente);
        return ResponseEntity.created(new URI("/api/clientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /clientes : Updates an existing cliente.
     *
     * @param cliente the cliente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cliente,
     * or with status 400 (Bad Request) if the cliente is not valid,
     * or with status 500 (Internal Server Error) if the cliente couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/clientes")
    @Timed
    public ResponseEntity<Cliente> updateCliente(@RequestBody Cliente cliente) throws URISyntaxException {
        log.debug("REST request to update Cliente : {}", cliente);
        if (cliente.getId() == null) {
            return createCliente(cliente);
        }
        Cliente result = clienteService.save(cliente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cliente.getId().toString()))
            .body(result);
    }

    /**
     * GET  /clientes : get all the clientes.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of clientes in body
     */
    @GetMapping("/clientes")
    @Timed
    public ResponseEntity<List<Cliente>> getAllClientes(ClienteCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Clientes by criteria: {}", criteria);
        Page<Cliente> page = clienteQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /clientes/:id : get the "id" cliente.
     *
     * @param id the id of the cliente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cliente, or with status 404 (Not Found)
     */
    @GetMapping("/clientes/{id}")
    @Timed
    public ResponseEntity<Cliente> getCliente(@PathVariable Long id) {
        log.debug("REST request to get Cliente : {}", id);
        Cliente cliente = clienteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(cliente));
    }

    /**
     * DELETE  /clientes/:id : delete the "id" cliente.
     *
     * @param id the id of the cliente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/clientes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        log.debug("REST request to delete Cliente : {}", id);
        clienteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/clientes?query=:query : search for the cliente corresponding
     * to the query.
     *
     * @param query the query of the cliente search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/clientes")
    @Timed
    public ResponseEntity<List<Cliente>> searchClientes(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Clientes for query {}", query);
        Page<Cliente> page = clienteService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/clientes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
