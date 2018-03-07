package com.outscape.noon07.service.impl;

import com.outscape.noon07.service.ComandaService;
import com.outscape.noon07.domain.Comanda;
import com.outscape.noon07.repository.ComandaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Comanda.
 */
@Service
@Transactional
public class ComandaServiceImpl implements ComandaService {

    private final Logger log = LoggerFactory.getLogger(ComandaServiceImpl.class);

    private final ComandaRepository comandaRepository;

    public ComandaServiceImpl(ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    /**
     * Save a comanda.
     *
     * @param comanda the entity to save
     * @return the persisted entity
     */
    @Override
    public Comanda save(Comanda comanda) {
        log.debug("Request to save Comanda : {}", comanda);
        return comandaRepository.save(comanda);
    }

    /**
     * Get all the comandas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Comanda> findAll(Pageable pageable) {
        log.debug("Request to get all Comandas");
        return comandaRepository.findAll(pageable);
    }

    /**
     * Get one comanda by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Comanda findOne(Long id) {
        log.debug("Request to get Comanda : {}", id);
        return comandaRepository.findOne(id);
    }

    /**
     * Delete the comanda by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comanda : {}", id);
        comandaRepository.delete(id);
    }
}
