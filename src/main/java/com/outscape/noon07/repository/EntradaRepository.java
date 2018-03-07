package com.outscape.noon07.repository;

import com.outscape.noon07.domain.Entrada;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Entrada entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long>, JpaSpecificationExecutor<Entrada> {

}
