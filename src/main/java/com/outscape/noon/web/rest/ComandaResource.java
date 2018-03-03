package com.outscape.noon.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.outscape.noon.domain.Comanda;
import com.outscape.noon.service.ComandaService;
import com.outscape.noon.web.rest.errors.BadRequestAlertException;
import com.outscape.noon.web.rest.util.HeaderUtil;
import com.outscape.noon.web.rest.util.PaginationUtil;
import com.outscape.noon.service.dto.ComandaCriteria;
import com.outscape.noon.service.ComandaQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Comanda.
 */
@RestController
@RequestMapping("/api")
public class ComandaResource {

    private final Logger log = LoggerFactory.getLogger(ComandaResource.class);

    private static final String ENTITY_NAME = "comanda";

    private final ComandaService comandaService;

    private final ComandaQueryService comandaQueryService;

    public ComandaResource(ComandaService comandaService, ComandaQueryService comandaQueryService) {
        this.comandaService = comandaService;
        this.comandaQueryService = comandaQueryService;
    }

    /**
     * POST  /comandas : Create a new comanda.
     *
     * @param comanda the comanda to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comanda, or with status 400 (Bad Request) if the comanda has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comandas")
    @Timed
    public ResponseEntity<Comanda> createComanda(@Valid @RequestBody Comanda comanda) throws URISyntaxException {
        log.debug("REST request to save Comanda : {}", comanda);
        if (comanda.getId() != null) {
            throw new BadRequestAlertException("A new comanda cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Comanda result = comandaService.save(comanda);
        return ResponseEntity.created(new URI("/api/comandas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comandas : Updates an existing comanda.
     *
     * @param comanda the comanda to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comanda,
     * or with status 400 (Bad Request) if the comanda is not valid,
     * or with status 500 (Internal Server Error) if the comanda couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comandas")
    @Timed
    public ResponseEntity<Comanda> updateComanda(@Valid @RequestBody Comanda comanda) throws URISyntaxException {
        log.debug("REST request to update Comanda : {}", comanda);
        if (comanda.getId() == null) {
            return createComanda(comanda);
        }
        Comanda result = comandaService.save(comanda);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, comanda.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comandas : get all the comandas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of comandas in body
     */
    @GetMapping("/comandas")
    @Timed
    public ResponseEntity<List<Comanda>> getAllComandas(ComandaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Comandas by criteria: {}", criteria);
        Page<Comanda> page = comandaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comandas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comandas/:id : get the "id" comanda.
     *
     * @param id the id of the comanda to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comanda, or with status 404 (Not Found)
     */
    @GetMapping("/comandas/{id}")
    @Timed
    public ResponseEntity<Comanda> getComanda(@PathVariable Long id) {
        log.debug("REST request to get Comanda : {}", id);
        Comanda comanda = comandaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(comanda));
    }

    /**
     * DELETE  /comandas/:id : delete the "id" comanda.
     *
     * @param id the id of the comanda to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comandas/{id}")
    @Timed
    public ResponseEntity<Void> deleteComanda(@PathVariable Long id) {
        log.debug("REST request to delete Comanda : {}", id);
        comandaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/comandas?query=:query : search for the comanda corresponding
     * to the query.
     *
     * @param query the query of the comanda search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/comandas")
    @Timed
    public ResponseEntity<List<Comanda>> searchComandas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Comandas for query {}", query);
        Page<Comanda> page = comandaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/comandas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
