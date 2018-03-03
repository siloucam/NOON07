package com.outscape.noon.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.outscape.noon.domain.Comanda;
import com.outscape.noon.domain.*; // for static metamodels
import com.outscape.noon.repository.ComandaRepository;
import com.outscape.noon.repository.search.ComandaSearchRepository;
import com.outscape.noon.service.dto.ComandaCriteria;

import com.outscape.noon.domain.enumeration.StatusComanda;

/**
 * Service for executing complex queries for Comanda entities in the database.
 * The main input is a {@link ComandaCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Comanda} or a {@link Page} of {@link Comanda} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ComandaQueryService extends QueryService<Comanda> {

    private final Logger log = LoggerFactory.getLogger(ComandaQueryService.class);


    private final ComandaRepository comandaRepository;

    private final ComandaSearchRepository comandaSearchRepository;

    public ComandaQueryService(ComandaRepository comandaRepository, ComandaSearchRepository comandaSearchRepository) {
        this.comandaRepository = comandaRepository;
        this.comandaSearchRepository = comandaSearchRepository;
    }

    /**
     * Return a {@link List} of {@link Comanda} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Comanda> findByCriteria(ComandaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Comanda> specification = createSpecification(criteria);
        return comandaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Comanda} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Comanda> findByCriteria(ComandaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Comanda> specification = createSpecification(criteria);
        return comandaRepository.findAll(specification, page);
    }

    /**
     * Function to convert ComandaCriteria to a {@link Specifications}
     */
    private Specifications<Comanda> createSpecification(ComandaCriteria criteria) {
        Specifications<Comanda> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Comanda_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Comanda_.numero));
            }
            if (criteria.getData() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getData(), Comanda_.data));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Comanda_.total));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Comanda_.status));
            }
            if (criteria.getProdutoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getProdutoId(), Comanda_.produtos, ProdutoConsumido_.id));
            }
            if (criteria.getClienteId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getClienteId(), Comanda_.cliente, Cliente_.id));
            }
        }
        return specification;
    }

}
