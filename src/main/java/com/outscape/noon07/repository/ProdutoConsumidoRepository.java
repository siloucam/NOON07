package com.outscape.noon07.repository;

import com.outscape.noon07.domain.ProdutoConsumido;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProdutoConsumido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoConsumidoRepository extends JpaRepository<ProdutoConsumido, Long>, JpaSpecificationExecutor<ProdutoConsumido> {

}
