package com.outscape.noon.service;

import com.outscape.noon.domain.Comanda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Comanda.
 */
public interface ComandaService {

    /**
     * Save a comanda.
     *
     * @param comanda the entity to save
     * @return the persisted entity
     */
    Comanda save(Comanda comanda);

    /**
     * Get all the comandas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Comanda> findAll(Pageable pageable);

    /**
     * Get the "id" comanda.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Comanda findOne(Long id);

    /**
     * Delete the "id" comanda.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the comanda corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Comanda> search(String query, Pageable pageable);
}
