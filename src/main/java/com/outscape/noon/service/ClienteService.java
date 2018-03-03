package com.outscape.noon.service;

import com.outscape.noon.domain.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Cliente.
 */
public interface ClienteService {

    /**
     * Save a cliente.
     *
     * @param cliente the entity to save
     * @return the persisted entity
     */
    Cliente save(Cliente cliente);

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Cliente> findAll(Pageable pageable);

    /**
     * Get the "id" cliente.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Cliente findOne(Long id);

    /**
     * Delete the "id" cliente.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the cliente corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Cliente> search(String query, Pageable pageable);
}
