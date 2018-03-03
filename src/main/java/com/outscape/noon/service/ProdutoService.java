package com.outscape.noon.service;

import com.outscape.noon.domain.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Produto.
 */
public interface ProdutoService {

    /**
     * Save a produto.
     *
     * @param produto the entity to save
     * @return the persisted entity
     */
    Produto save(Produto produto);

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Produto> findAll(Pageable pageable);

    /**
     * Get the "id" produto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Produto findOne(Long id);

    /**
     * Delete the "id" produto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the produto corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Produto> search(String query, Pageable pageable);
}
