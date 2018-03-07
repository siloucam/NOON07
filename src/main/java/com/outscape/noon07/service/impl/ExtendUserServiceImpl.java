package com.outscape.noon07.service.impl;

import com.outscape.noon07.service.ExtendUserService;
import com.outscape.noon07.domain.ExtendUser;
import com.outscape.noon07.repository.ExtendUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExtendUser.
 */
@Service
@Transactional
public class ExtendUserServiceImpl implements ExtendUserService {

    private final Logger log = LoggerFactory.getLogger(ExtendUserServiceImpl.class);

    private final ExtendUserRepository extendUserRepository;

    public ExtendUserServiceImpl(ExtendUserRepository extendUserRepository) {
        this.extendUserRepository = extendUserRepository;
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
        return extendUserRepository.save(extendUser);
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
    }
}
