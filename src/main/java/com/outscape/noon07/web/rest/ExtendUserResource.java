package com.outscape.noon07.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.outscape.noon07.domain.ExtendUser;
import com.outscape.noon07.service.ExtendUserService;
import com.outscape.noon07.web.rest.errors.BadRequestAlertException;
import com.outscape.noon07.web.rest.util.HeaderUtil;
import com.outscape.noon07.web.rest.util.PaginationUtil;
import com.outscape.noon07.service.dto.ExtendUserCriteria;
import com.outscape.noon07.service.ExtendUserQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ExtendUser.
 */
@RestController
@RequestMapping("/api")
public class ExtendUserResource {

    private final Logger log = LoggerFactory.getLogger(ExtendUserResource.class);

    private static final String ENTITY_NAME = "extendUser";

    private final ExtendUserService extendUserService;

    private final ExtendUserQueryService extendUserQueryService;

    public ExtendUserResource(ExtendUserService extendUserService, ExtendUserQueryService extendUserQueryService) {
        this.extendUserService = extendUserService;
        this.extendUserQueryService = extendUserQueryService;
    }

    /**
     * POST  /extend-users : Create a new extendUser.
     *
     * @param extendUser the extendUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new extendUser, or with status 400 (Bad Request) if the extendUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/extend-users")
    @Timed
    public ResponseEntity<ExtendUser> createExtendUser(@RequestBody ExtendUser extendUser) throws URISyntaxException {
        log.debug("REST request to save ExtendUser : {}", extendUser);
        if (extendUser.getId() != null) {
            throw new BadRequestAlertException("A new extendUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExtendUser result = extendUserService.save(extendUser);
        return ResponseEntity.created(new URI("/api/extend-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /extend-users : Updates an existing extendUser.
     *
     * @param extendUser the extendUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated extendUser,
     * or with status 400 (Bad Request) if the extendUser is not valid,
     * or with status 500 (Internal Server Error) if the extendUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/extend-users")
    @Timed
    public ResponseEntity<ExtendUser> updateExtendUser(@RequestBody ExtendUser extendUser) throws URISyntaxException {
        log.debug("REST request to update ExtendUser : {}", extendUser);
        if (extendUser.getId() == null) {
            return createExtendUser(extendUser);
        }
        ExtendUser result = extendUserService.save(extendUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, extendUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /extend-users : get all the extendUsers.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of extendUsers in body
     */
    @GetMapping("/extend-users")
    @Timed
    public ResponseEntity<List<ExtendUser>> getAllExtendUsers(ExtendUserCriteria criteria, Pageable pageable) {
        log.debug("REST request to get ExtendUsers by criteria: {}", criteria);
        Page<ExtendUser> page = extendUserQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/extend-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /extend-users/:id : get the "id" extendUser.
     *
     * @param id the id of the extendUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the extendUser, or with status 404 (Not Found)
     */
    @GetMapping("/extend-users/{id}")
    @Timed
    public ResponseEntity<ExtendUser> getExtendUser(@PathVariable Long id) {
        log.debug("REST request to get ExtendUser : {}", id);
        ExtendUser extendUser = extendUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(extendUser));
    }

    /**
     * DELETE  /extend-users/:id : delete the "id" extendUser.
     *
     * @param id the id of the extendUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/extend-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteExtendUser(@PathVariable Long id) {
        log.debug("REST request to delete ExtendUser : {}", id);
        extendUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
