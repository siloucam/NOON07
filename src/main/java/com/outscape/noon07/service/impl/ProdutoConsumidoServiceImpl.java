package com.outscape.noon07.service.impl;

import com.outscape.noon07.service.ProdutoConsumidoService;
import com.outscape.noon07.domain.ProdutoConsumido;
import com.outscape.noon07.repository.ProdutoConsumidoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ProdutoConsumido.
 */
@Service
@Transactional
public class ProdutoConsumidoServiceImpl implements ProdutoConsumidoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoConsumidoServiceImpl.class);

    private final ProdutoConsumidoRepository produtoConsumidoRepository;

    public ProdutoConsumidoServiceImpl(ProdutoConsumidoRepository produtoConsumidoRepository) {
        this.produtoConsumidoRepository = produtoConsumidoRepository;
    }

    /**
     * Save a produtoConsumido.
     *
     * @param produtoConsumido the entity to save
     * @return the persisted entity
     */
    @Override
    public ProdutoConsumido save(ProdutoConsumido produtoConsumido) {
        log.debug("Request to save ProdutoConsumido : {}", produtoConsumido);
        return produtoConsumidoRepository.save(produtoConsumido);
    }

    /**
     * Get all the produtoConsumidos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProdutoConsumido> findAll(Pageable pageable) {
        log.debug("Request to get all ProdutoConsumidos");
        return produtoConsumidoRepository.findAll(pageable);
    }

    /**
     * Get one produtoConsumido by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProdutoConsumido findOne(Long id) {
        log.debug("Request to get ProdutoConsumido : {}", id);
        return produtoConsumidoRepository.findOne(id);
    }

    /**
     * Delete the produtoConsumido by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProdutoConsumido : {}", id);
        produtoConsumidoRepository.delete(id);
    }
}
