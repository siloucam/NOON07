package com.outscape.noon.repository;

import com.outscape.noon.domain.ProdutoConsumido;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProdutoConsumido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoConsumidoRepository extends JpaRepository<ProdutoConsumido, Long>, JpaSpecificationExecutor<ProdutoConsumido> {

}
