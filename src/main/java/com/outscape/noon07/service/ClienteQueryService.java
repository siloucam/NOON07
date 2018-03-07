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

import com.outscape.noon07.domain.Cliente;
import com.outscape.noon07.domain.*; // for static metamodels
import com.outscape.noon07.repository.ClienteRepository;
import com.outscape.noon07.service.dto.ClienteCriteria;


/**
 * Service for executing complex queries for Cliente entities in the database.
 * The main input is a {@link ClienteCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Cliente} or a {@link Page} of {@link Cliente} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ClienteQueryService extends QueryService<Cliente> {

    private final Logger log = LoggerFactory.getLogger(ClienteQueryService.class);


    private final ClienteRepository clienteRepository;

    public ClienteQueryService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Return a {@link List} of {@link Cliente} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Cliente> findByCriteria(ClienteCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Cliente> specification = createSpecification(criteria);
        return clienteRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Cliente} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Cliente> findByCriteria(ClienteCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Cliente> specification = createSpecification(criteria);
        return clienteRepository.findAll(specification, page);
    }

    /**
     * Function to convert ClienteCriteria to a {@link Specifications}
     */
    private Specifications<Cliente> createSpecification(ClienteCriteria criteria) {
        Specifications<Cliente> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Cliente_.id));
            }
            if (criteria.getNome() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNome(), Cliente_.nome));
            }
            if (criteria.getDocumento() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDocumento(), Cliente_.documento));
            }
            if (criteria.getTelefone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTelefone(), Cliente_.telefone));
            }
            if (criteria.getComandaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getComandaId(), Cliente_.comandas, Comanda_.id));
            }
        }
        return specification;
    }

}
