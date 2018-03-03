package com.outscape.noon.service;

import com.outscape.noon.domain.ExtendUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExtendUser.
 */
public interface ExtendUserService {

    /**
     * Save a extendUser.
     *
     * @param extendUser the entity to save
     * @return the persisted entity
     */
    ExtendUser save(ExtendUser extendUser);

    /**
     * Get all the extendUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExtendUser> findAll(Pageable pageable);

    /**
     * Get the "id" extendUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExtendUser findOne(Long id);

    /**
     * Delete the "id" extendUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the extendUser corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExtendUser> search(String query, Pageable pageable);
}
