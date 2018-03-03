package com.outscape.noon.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.outscape.noon.domain.ProdutoConsumido;
import com.outscape.noon.service.ProdutoConsumidoService;
import com.outscape.noon.web.rest.errors.BadRequestAlertException;
import com.outscape.noon.web.rest.util.HeaderUtil;
import com.outscape.noon.web.rest.util.PaginationUtil;
import com.outscape.noon.service.dto.ProdutoConsumidoCriteria;
import com.outscape.noon.service.ProdutoConsumidoQueryService;
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
 * REST controller for managing ProdutoConsumido.
 */
@RestController
@RequestMapping("/api")
public class ProdutoConsumidoResource {

    private final Logger log = LoggerFactory.getLogger(ProdutoConsumidoResource.class);

    private static final String ENTITY_NAME = "produtoConsumido";

    private final ProdutoConsumidoService produtoConsumidoService;

    private final ProdutoConsumidoQueryService produtoConsumidoQueryService;

    public ProdutoConsumidoResource(ProdutoConsumidoService produtoConsumidoService, ProdutoConsumidoQueryService produtoConsumidoQueryService) {
        this.produtoConsumidoService = produtoConsumidoService;
        this.produtoConsumidoQueryService = produtoConsumidoQueryService;
    }

    /**
     * POST  /produto-consumidos : Create a new produtoConsumido.
     *
     * @param produtoConsumido the produtoConsumido to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produtoConsumido, or with status 400 (Bad Request) if the produtoConsumido has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produto-consumidos")
    @Timed
    public ResponseEntity<ProdutoConsumido> createProdutoConsumido(@RequestBody ProdutoConsumido produtoConsumido) throws URISyntaxException {
        log.debug("REST request to save ProdutoConsumido : {}", produtoConsumido);
        if (produtoConsumido.getId() != null) {
            throw new BadRequestAlertException("A new produtoConsumido cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProdutoConsumido result = produtoConsumidoService.save(produtoConsumido);
        return ResponseEntity.created(new URI("/api/produto-consumidos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produto-consumidos : Updates an existing produtoConsumido.
     *
     * @param produtoConsumido the produtoConsumido to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produtoConsumido,
     * or with status 400 (Bad Request) if the produtoConsumido is not valid,
     * or with status 500 (Internal Server Error) if the produtoConsumido couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produto-consumidos")
    @Timed
    public ResponseEntity<ProdutoConsumido> updateProdutoConsumido(@RequestBody ProdutoConsumido produtoConsumido) throws URISyntaxException {
        log.debug("REST request to update ProdutoConsumido : {}", produtoConsumido);
        if (produtoConsumido.getId() == null) {
            return createProdutoConsumido(produtoConsumido);
        }
        ProdutoConsumido result = produtoConsumidoService.save(produtoConsumido);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produtoConsumido.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produto-consumidos : get all the produtoConsumidos.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of produtoConsumidos in body
     */
    @GetMapping("/produto-consumidos")
    @Timed
    public ResponseEntity<List<ProdutoConsumido>> getAllProdutoConsumidos(ProdutoConsumidoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ProdutoConsumidos by criteria: {}", criteria);
        Page<ProdutoConsumido> page = produtoConsumidoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produto-consumidos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produto-consumidos/:id : get the "id" produtoConsumido.
     *
     * @param id the id of the produtoConsumido to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produtoConsumido, or with status 404 (Not Found)
     */
    @GetMapping("/produto-consumidos/{id}")
    @Timed
    public ResponseEntity<ProdutoConsumido> getProdutoConsumido(@PathVariable Long id) {
        log.debug("REST request to get ProdutoConsumido : {}", id);
        ProdutoConsumido produtoConsumido = produtoConsumidoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produtoConsumido));
    }

    /**
     * DELETE  /produto-consumidos/:id : delete the "id" produtoConsumido.
     *
     * @param id the id of the produtoConsumido to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produto-consumidos/{id}")
    @Timed
    public ResponseEntity<Void> deleteProdutoConsumido(@PathVariable Long id) {
        log.debug("REST request to delete ProdutoConsumido : {}", id);
        produtoConsumidoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/produto-consumidos?query=:query : search for the produtoConsumido corresponding
     * to the query.
     *
     * @param query the query of the produtoConsumido search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/produto-consumidos")
    @Timed
    public ResponseEntity<List<ProdutoConsumido>> searchProdutoConsumidos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ProdutoConsumidos for query {}", query);
        Page<ProdutoConsumido> page = produtoConsumidoService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/produto-consumidos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
