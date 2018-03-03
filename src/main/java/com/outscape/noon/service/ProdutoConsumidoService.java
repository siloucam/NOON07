package com.outscape.noon.service;

import com.outscape.noon.domain.ProdutoConsumido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProdutoConsumido.
 */
public interface ProdutoConsumidoService {

    /**
     * Save a produtoConsumido.
     *
     * @param produtoConsumido the entity to save
     * @return the persisted entity
     */
    ProdutoConsumido save(ProdutoConsumido produtoConsumido);

    /**
     * Get all the produtoConsumidos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProdutoConsumido> findAll(Pageable pageable);

    /**
     * Get the "id" produtoConsumido.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProdutoConsumido findOne(Long id);

    /**
     * Delete the "id" produtoConsumido.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the produtoConsumido corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProdutoConsumido> search(String query, Pageable pageable);
}
