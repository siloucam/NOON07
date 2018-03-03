package com.outscape.noon.service.impl;

import com.outscape.noon.service.ExtendUserService;
import com.outscape.noon.domain.ExtendUser;
import com.outscape.noon.repository.ExtendUserRepository;
import com.outscape.noon.repository.search.ExtendUserSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ExtendUser.
 */
@Service
@Transactional
public class ExtendUserServiceImpl implements ExtendUserService {

    private final Logger log = LoggerFactory.getLogger(ExtendUserServiceImpl.class);

    private final ExtendUserRepository extendUserRepository;

    private final ExtendUserSearchRepository extendUserSearchRepository;

    public ExtendUserServiceImpl(ExtendUserRepository extendUserRepository, ExtendUserSearchRepository extendUserSearchRepository) {
        this.extendUserRepository = extendUserRepository;
        this.extendUserSearchRepository = extendUserSearchRepository;
    }

    /**
     * Save a extendUser.
     *
     * @param extendUser the entity to save
     * @return the persisted entity
     */
    @Override
    public ExtendUser save(ExtendUser extendUser) {
        log.debug("Request to save ExtendUser : {}", extendUser);
        ExtendUser result = extendUserRepository.save(extendUser);
        extendUserSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the extendUsers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExtendUser> findAll(Pageable pageable) {
        log.debug("Request to get all ExtendUsers");
        return extendUserRepository.findAll(pageable);
    }

    /**
     * Get one extendUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExtendUser findOne(Long id) {
        log.debug("Request to get ExtendUser : {}", id);
        return extendUserRepository.findOne(id);
    }

    /**
     * Delete the extendUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExtendUser : {}", id);
        extendUserRepository.delete(id);
        extendUserSearchRepository.delete(id);
    }

    /**
     * Search for the extendUser corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExtendUser> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ExtendUsers for query {}", query);
        Page<ExtendUser> result = extendUserSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
