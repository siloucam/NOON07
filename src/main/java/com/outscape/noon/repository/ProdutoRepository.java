package com.outscape.noon.repository;

import com.outscape.noon.domain.Produto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Produto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>, JpaSpecificationExecutor<Produto> {

}
