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

import com.outscape.noon07.domain.Produto;
import com.outscape.noon07.domain.*; // for static metamodels
import com.outscape.noon07.repository.ProdutoRepository;
import com.outscape.noon07.service.dto.ProdutoCriteria;


/**
 * Service for executing complex queries for Produto entities in the database.
 * The main input is a {@link ProdutoCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Produto} or a {@link Page} of {@link Produto} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdutoQueryService extends QueryService<Produto> {

    private final Logger log = LoggerFactory.getLogger(ProdutoQueryService.class);


    private final ProdutoRepository produtoRepository;

    public ProdutoQueryService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Return a {@link List} of {@link Produto} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Produto> findByCriteria(ProdutoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Produto> specification = createSpecification(criteria);
        return produtoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Produto} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Produto> findByCriteria(ProdutoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Produto> specification = createSpecification(criteria);
        return produtoRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProdutoCriteria to a {@link Specifications}
     */
    private Specifications<Produto> createSpecification(ProdutoCriteria criteria) {
        Specifications<Produto> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Produto_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Produto_.nome));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), Produto_.valor));
            }
        }
        return specification;
    }

}
