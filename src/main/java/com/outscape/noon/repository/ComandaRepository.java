package com.outscape.noon.repository;

import com.outscape.noon.domain.Comanda;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Comanda entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long>, JpaSpecificationExecutor<Comanda> {

}
