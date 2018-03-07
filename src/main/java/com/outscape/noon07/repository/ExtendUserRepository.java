package com.outscape.noon07.repository;

import com.outscape.noon07.domain.ExtendUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ExtendUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtendUserRepository extends JpaRepository<ExtendUser, Long>, JpaSpecificationExecutor<ExtendUser> {

}
