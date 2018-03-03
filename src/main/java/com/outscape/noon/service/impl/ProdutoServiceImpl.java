package com.outscape.noon.service.impl;

import com.outscape.noon.service.ProdutoService;
import com.outscape.noon.domain.Produto;
import com.outscape.noon.repository.ProdutoRepository;
import com.outscape.noon.repository.search.ProdutoSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Produto.
 */
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    private final ProdutoRepository produtoRepository;

    private final ProdutoSearchRepository produtoSearchRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, ProdutoSearchRepository produtoSearchRepository) {
        this.produtoRepository = produtoRepository;
        this.produtoSearchRepository = produtoSearchRepository;
    }

    /**
     * Save a produto.
     *
     * @param produto the entity to save
     * @return the persisted entity
     */
    @Override
    public Produto save(Produto produto) {
        log.debug("Request to save Produto : {}", produto);
        Produto result = produtoRepository.save(produto);
        produtoSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produto> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        return produtoRepository.findAll(pageable);
    }

    /**
     * Get one produto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Produto findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        return produtoRepository.findOne(id);
    }

    /**
     * Delete the produto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.delete(id);
        produtoSearchRepository.delete(id);
    }

    /**
     * Search for the produto corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produto> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Produtos for query {}", query);
        Page<Produto> result = produtoSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
