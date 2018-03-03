package com.outscape.noon.service.impl;

import com.outscape.noon.service.ProdutoConsumidoService;
import com.outscape.noon.domain.ProdutoConsumido;
import com.outscape.noon.repository.ProdutoConsumidoRepository;
import com.outscape.noon.repository.search.ProdutoConsumidoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ProdutoConsumido.
 */
@Service
@Transactional
public class ProdutoConsumidoServiceImpl implements ProdutoConsumidoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoConsumidoServiceImpl.class);

    private final ProdutoConsumidoRepository produtoConsumidoRepository;

    private final ProdutoConsumidoSearchRepository produtoConsumidoSearchRepository;

    public ProdutoConsumidoServiceImpl(ProdutoConsumidoRepository produtoConsumidoRepository, ProdutoConsumidoSearchRepository produtoConsumidoSearchRepository) {
        this.produtoConsumidoRepository = produtoConsumidoRepository;
        this.produtoConsumidoSearchRepository = produtoConsumidoSearchRepository;
    }

    /**
     * Save a produtoConsumido.
     *
     * @param produtoConsumido the entity to save
     * @return the persisted entity
     */
    @Override
    public ProdutoConsumido save(ProdutoConsumido produtoConsumido) {
        log.debug("Request to save ProdutoConsumido : {}", produtoConsumido);
        ProdutoConsumido result = produtoConsumidoRepository.save(produtoConsumido);
        produtoConsumidoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the produtoConsumidos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoConsumido> findAll(Pageable pageable) {
        log.debug("Request to get all ProdutoConsumidos");
        return produtoConsumidoRepository.findAll(pageable);
    }

    /**
     * Get one produtoConsumido by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProdutoConsumido findOne(Long id) {
        log.debug("Request to get ProdutoConsumido : {}", id);
        return produtoConsumidoRepository.findOne(id);
    }

    /**
     * Delete the produtoConsumido by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProdutoConsumido : {}", id);
        produtoConsumidoRepository.delete(id);
        produtoConsumidoSearchRepository.delete(id);
    }

    /**
     * Search for the produtoConsumido corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoConsumido> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ProdutoConsumidos for query {}", query);
        Page<ProdutoConsumido> result = produtoConsumidoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
