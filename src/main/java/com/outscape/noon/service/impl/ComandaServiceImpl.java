package com.outscape.noon.service.impl;

import com.outscape.noon.service.ComandaService;
import com.outscape.noon.domain.Comanda;
import com.outscape.noon.repository.ComandaRepository;
import com.outscape.noon.repository.search.ComandaSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Comanda.
 */
@Service
@Transactional
public class ComandaServiceImpl implements ComandaService {

    private final Logger log = LoggerFactory.getLogger(ComandaServiceImpl.class);

    private final ComandaRepository comandaRepository;

    private final ComandaSearchRepository comandaSearchRepository;

    public ComandaServiceImpl(ComandaRepository comandaRepository, ComandaSearchRepository comandaSearchRepository) {
        this.comandaRepository = comandaRepository;
        this.comandaSearchRepository = comandaSearchRepository;
    }

    /**
     * Save a comanda.
     *
     * @param comanda the entity to save
     * @return the persisted entity
     */
    @Override
    public Comanda save(Comanda comanda) {
        log.debug("Request to save Comanda : {}", comanda);
        Comanda result = comandaRepository.save(comanda);
        comandaSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the comandas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Comanda> findAll(Pageable pageable) {
        log.debug("Request to get all Comandas");
        return comandaRepository.findAll(pageable);
    }

    /**
     * Get one comanda by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Comanda findOne(Long id) {
        log.debug("Request to get Comanda : {}", id);
        return comandaRepository.findOne(id);
    }

    /**
     * Delete the comanda by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comanda : {}", id);
        comandaRepository.delete(id);
        comandaSearchRepository.delete(id);
    }

    /**
     * Search for the comanda corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Comanda> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Comandas for query {}", query);
        Page<Comanda> result = comandaSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
