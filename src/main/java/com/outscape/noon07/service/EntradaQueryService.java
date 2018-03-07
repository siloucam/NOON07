package com.outscape.noon07.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.outscape.noon07.domain.Entrada;
import com.outscape.noon07.domain.*; // for static metamodels
import com.outscape.noon07.repository.EntradaRepository;
import com.outscape.noon07.service.dto.EntradaCriteria;


/**
 * Service for executing complex queries for Entrada entities in the database.
 * The main input is a {@link EntradaCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Entrada} or a {@link Page} of {@link Entrada} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EntradaQueryService extends QueryService<Entrada> {

    private final Logger log = LoggerFactory.getLogger(EntradaQueryService.class);


    private final EntradaRepository entradaRepository;

    public EntradaQueryService(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    /**
     * Return a {@link List} of {@link Entrada} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Entrada> findByCriteria(EntradaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Entrada> specification = createSpecification(criteria);
        return entradaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Entrada} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Entrada> findByCriteria(EntradaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Entrada> specification = createSpecification(criteria);
        return entradaRepository.findAll(specification, page);
    }

    /**
     * Function to convert EntradaCriteria to a {@link Specifications}
     */
    private Specifications<Entrada> createSpecification(EntradaCriteria criteria) {
        Specifications<Entrada> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Entrada_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Entrada_.nome));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Entrada_.valor));
            }
        }
        return specification;
    }

}
