package com.outscape.noon07.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.outscape.noon07.domain.Entrada;
import com.outscape.noon07.service.EntradaService;
import com.outscape.noon07.web.rest.errors.BadRequestAlertException;
import com.outscape.noon07.web.rest.util.HeaderUtil;
import com.outscape.noon07.web.rest.util.PaginationUtil;
import com.outscape.noon07.service.dto.EntradaCriteria;
import com.outscape.noon07.service.EntradaQueryService;
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

/**
 * REST controller for managing Entrada.
 */
@RestController
@RequestMapping("/api")
public class EntradaResource {

    private final Logger log = LoggerFactory.getLogger(EntradaResource.class);

    private static final String ENTITY_NAME = "entrada";

    private final EntradaService entradaService;

    private final EntradaQueryService entradaQueryService;

    public EntradaResource(EntradaService entradaService, EntradaQueryService entradaQueryService) {
        this.entradaService = entradaService;
        this.entradaQueryService = entradaQueryService;
    }

    /**
     * POST  /entradas : Create a new entrada.
     *
     * @param entrada the entrada to create
     * @return the ResponseEntity with status 201 (Created) and with body the new entrada, or with status 400 (Bad Request) if the entrada has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/entradas")
    @Timed
    public ResponseEntity<Entrada> createEntrada(@RequestBody Entrada entrada) throws URISyntaxException {
        log.debug("REST request to save Entrada : {}", entrada);
        if (entrada.getId() != null) {
            throw new BadRequestAlertException("A new entrada cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Entrada result = entradaService.save(entrada);
        return ResponseEntity.created(new URI("/api/entradas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /entradas : Updates an existing entrada.
     *
     * @param entrada the entrada to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated entrada,
     * or with status 400 (Bad Request) if the entrada is not valid,
     * or with status 500 (Internal Server Error) if the entrada couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/entradas")
    @Timed
    public ResponseEntity<Entrada> updateEntrada(@RequestBody Entrada entrada) throws URISyntaxException {
        log.debug("REST request to update Entrada : {}", entrada);
        if (entrada.getId() == null) {
            return createEntrada(entrada);
        }
        Entrada result = entradaService.save(entrada);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, entrada.getId().toString()))
            .body(result);
    }

    /**
     * GET  /entradas : get all the entradas.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of entradas in body
     */
    @GetMapping("/entradas")
    @Timed
    public ResponseEntity<List<Entrada>> getAllEntradas(EntradaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Entradas by criteria: {}", criteria);
        Page<Entrada> page = entradaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/entradas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /entradas/:id : get the "id" entrada.
     *
     * @param id the id of the entrada to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the entrada, or with status 404 (Not Found)
     */
    @GetMapping("/entradas/{id}")
    @Timed
    public ResponseEntity<Entrada> getEntrada(@PathVariable Long id) {
        log.debug("REST request to get Entrada : {}", id);
        Entrada entrada = entradaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(entrada));
    }

    /**
     * DELETE  /entradas/:id : delete the "id" entrada.
     *
     * @param id the id of the entrada to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/entradas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEntrada(@PathVariable Long id) {
        log.debug("REST request to delete Entrada : {}", id);
        entradaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
