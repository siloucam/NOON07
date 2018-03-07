package com.outscape.noon07.service;

import com.outscape.noon07.domain.Entrada;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Entrada.
 */
public interface EntradaService {

    /**
     * Save a entrada.
     *
     * @param entrada the entity to save
     * @return the persisted entity
     */
    Entrada save(Entrada entrada);

    /**
     * Get all the entradas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Entrada> findAll(Pageable pageable);

    /**
     * Get the "id" entrada.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Entrada findOne(Long id);

    /**
     * Delete the "id" entrada.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
