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

import com.outscape.noon07.domain.ProdutoConsumido;
import com.outscape.noon07.domain.*; // for static metamodels
import com.outscape.noon07.repository.ProdutoConsumidoRepository;
import com.outscape.noon07.service.dto.ProdutoConsumidoCriteria;


/**
 * Service for executing complex queries for ProdutoConsumido entities in the database.
 * The main input is a {@link ProdutoConsumidoCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProdutoConsumido} or a {@link Page} of {@link ProdutoConsumido} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProdutoConsumidoQueryService extends QueryService<ProdutoConsumido> {

    private final Logger log = LoggerFactory.getLogger(ProdutoConsumidoQueryService.class);


    private final ProdutoConsumidoRepository produtoConsumidoRepository;

    public ProdutoConsumidoQueryService(ProdutoConsumidoRepository produtoConsumidoRepository) {
        this.produtoConsumidoRepository = produtoConsumidoRepository;
    }

    /**
     * Return a {@link List} of {@link ProdutoConsumido} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProdutoConsumido> findByCriteria(ProdutoConsumidoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<ProdutoConsumido> specification = createSpecification(criteria);
        return produtoConsumidoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProdutoConsumido} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProdutoConsumido> findByCriteria(ProdutoConsumidoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<ProdutoConsumido> specification = createSpecification(criteria);
        return produtoConsumidoRepository.findAll(specification, page);
    }

    /**
     * Function to convert ProdutoConsumidoCriteria to a {@link Specifications}
     */
    private Specifications<ProdutoConsumido> createSpecification(ProdutoConsumidoCriteria criteria) {
        Specifications<ProdutoConsumido> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProdutoConsumido_.id));
            }
            if (criteria.getIdproduto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdproduto(), ProdutoConsumido_.idproduto));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), ProdutoConsumido_.nome));
            }
            if (criteria.getValor() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getValor(), ProdutoConsumido_.valor));
            }
            if (criteria.getQuantidade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantidade(), ProdutoConsumido_.quantidade));
            }
            if (criteria.getIdentrada() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getIdentrada(), ProdutoConsumido_.identrada));
            }
            if (criteria.getComandaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getComandaId(), ProdutoConsumido_.comanda, Comanda_.id));
            }
        }
        return specification;
    }

}
