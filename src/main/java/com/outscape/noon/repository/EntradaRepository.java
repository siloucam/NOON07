package com.outscape.noon.repository;

import com.outscape.noon.domain.Entrada;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Entrada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long>, JpaSpecificationExecutor<Entrada> {

}
