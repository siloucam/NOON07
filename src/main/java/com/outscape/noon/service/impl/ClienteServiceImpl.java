package com.outscape.noon.service.impl;

import com.outscape.noon.service.ClienteService;
import com.outscape.noon.domain.Cliente;
import com.outscape.noon.repository.ClienteRepository;
import com.outscape.noon.repository.search.ClienteSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Cliente.
 */
@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    private final ClienteSearchRepository clienteSearchRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteSearchRepository clienteSearchRepository) {
        this.clienteRepository = clienteRepository;
        this.clienteSearchRepository = clienteSearchRepository;
    }

    /**
     * Save a cliente.
     *
     * @param cliente the entity to save
     * @return the persisted entity
     */
    @Override
    public Cliente save(Cliente cliente) {
        log.debug("Request to save Cliente : {}", cliente);
        Cliente result = clienteRepository.save(cliente);
        clienteSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the clientes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        log.debug("Request to get all Clientes");
        return clienteRepository.findAll(pageable);
    }

    /**
     * Get one cliente by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        log.debug("Request to get Cliente : {}", id);
        return clienteRepository.findOne(id);
    }

    /**
     * Delete the cliente by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cliente : {}", id);
        clienteRepository.delete(id);
        clienteSearchRepository.delete(id);
    }

    /**
     * Search for the cliente corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Clientes for query {}", query);
        Page<Cliente> result = clienteSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
