package com.outscape.noon.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.outscape.noon.domain.ExtendUser;
import com.outscape.noon.domain.*; // for static metamodels
import com.outscape.noon.repository.ExtendUserRepository;
import com.outscape.noon.repository.search.ExtendUserSearchRepository;
import com.outscape.noon.service.dto.ExtendUserCriteria;

import com.outscape.noon.domain.enumeration.Setor;

/**
 * Service for executing complex queries for ExtendUser entities in the database.
 * The main input is a {@link ExtendUserCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ExtendUser} or a {@link Page} of {@link ExtendUser} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ExtendUserQueryService extends QueryService<ExtendUser> {

    private final Logger log = LoggerFactory.getLogger(ExtendUserQueryService.class);


    private final ExtendUserRepository extendUserRepository;

    private final ExtendUserSearchRepository extendUserSearchRepository;

    public ExtendUserQueryService(ExtendUserRepository extendUserRepository, ExtendUserSearchRepository extendUserSearchRepository) {
        this.extendUserRepository = extendUserRepository;
        this.extendUserSearchRepository = extendUserSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ExtendUser} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ExtendUser> findByCriteria(ExtendUserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ExtendUser> specification = createSpecification(criteria);
        return extendUserRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ExtendUser} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ExtendUser> findByCriteria(ExtendUserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ExtendUser> specification = createSpecification(criteria);
        return extendUserRepository.findAll(specification, page);
    }

    /**
     * Function to convert ExtendUserCriteria to a {@link Specifications}
     */
    private Specifications<ExtendUser> createSpecification(ExtendUserCriteria criteria) {
        Specifications<ExtendUser> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ExtendUser_.id));
            }
            if (criteria.getSetor() != null) {
                specification = specification.and(buildSpecification(criteria.getSetor(), ExtendUser_.setor));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getUserId(), ExtendUser_.user, User_.id));
            }
        }
        return specification;
    }

}
