package com.outscape.noon07.service.impl;

import com.outscape.noon07.service.EntradaService;
import com.outscape.noon07.domain.Entrada;
import com.outscape.noon07.repository.EntradaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Entrada.
 */
@Service
@Transactional
public class EntradaServiceImpl implements EntradaService {

    private final Logger log = LoggerFactory.getLogger(EntradaServiceImpl.class);

    private final EntradaRepository entradaRepository;

    public EntradaServiceImpl(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    /**
     * Save a entrada.
     *
     * @param entrada the entity to save
     * @return the persisted entity
     */
    @Override
    public Entrada save(Entrada entrada) {
        log.debug("Request to save Entrada : {}", entrada);
        return entradaRepository.save(entrada);
    }

    /**
     * Get all the entradas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Entrada> findAll(Pageable pageable) {
        log.debug("Request to get all Entradas");
        return entradaRepository.findAll(pageable);
    }

    /**
     * Get one entrada by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Entrada findOne(Long id) {
        log.debug("Request to get Entrada : {}", id);
        return entradaRepository.findOne(id);
    }

    /**
     * Delete the entrada by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Entrada : {}", id);
        entradaRepository.delete(id);
    }
}
