package com.outscape.noon.service;

import com.outscape.noon.domain.Entrada;
import com.outscape.noon.repository.EntradaRepository;
import com.outscape.noon.repository.search.EntradaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Entrada.
 */
@Service
@Transactional
public class EntradaService {

    private final Logger log = LoggerFactory.getLogger(EntradaService.class);

    private final EntradaRepository entradaRepository;

    private final EntradaSearchRepository entradaSearchRepository;

    public EntradaService(EntradaRepository entradaRepository, EntradaSearchRepository entradaSearchRepository) {
        this.entradaRepository = entradaRepository;
        this.entradaSearchRepository = entradaSearchRepository;
    }

    /**
     * Save a entrada.
     *
     * @param entrada the entity to save
     * @return the persisted entity
     */
    public Entrada save(Entrada entrada) {
        log.debug("Request to save Entrada : {}", entrada);
        Entrada result = entradaRepository.save(entrada);
        entradaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the entradas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Entrada> findAll(Pageable pageable) {
        log.debug("Request to get all Entradas");
        return entradaRepository.findAll(pageable);
    }

    /**
     * Get one entrada by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Entrada findOne(Long id) {
        log.debug("Request to get Entrada : {}", id);
        return entradaRepository.findOne(id);
    }

    /**
     * Delete the entrada by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Entrada : {}", id);
        entradaRepository.delete(id);
        entradaSearchRepository.delete(id);
    }

    /**
     * Search for the entrada corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Entrada> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Entradas for query {}", query);
        Page<Entrada> result = entradaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
