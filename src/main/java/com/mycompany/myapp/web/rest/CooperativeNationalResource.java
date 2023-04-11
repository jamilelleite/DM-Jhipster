package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CooperativeNationalRepository;
import com.mycompany.myapp.service.CooperativeNationalService;
import com.mycompany.myapp.service.dto.CooperativeNationalDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.CooperativeNational}.
 */
@RestController
@RequestMapping("/api")
public class CooperativeNationalResource {

    private final Logger log = LoggerFactory.getLogger(CooperativeNationalResource.class);

    private static final String ENTITY_NAME = "cooperativeNational";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CooperativeNationalService cooperativeNationalService;

    private final CooperativeNationalRepository cooperativeNationalRepository;

    public CooperativeNationalResource(
        CooperativeNationalService cooperativeNationalService,
        CooperativeNationalRepository cooperativeNationalRepository
    ) {
        this.cooperativeNationalService = cooperativeNationalService;
        this.cooperativeNationalRepository = cooperativeNationalRepository;
    }

    /**
     * {@code POST  /cooperative-nationals} : Create a new cooperativeNational.
     *
     * @param cooperativeNationalDTO the cooperativeNationalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cooperativeNationalDTO, or with status {@code 400 (Bad Request)} if the cooperativeNational has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cooperative-nationals")
    public ResponseEntity<CooperativeNationalDTO> createCooperativeNational(
        @Valid @RequestBody CooperativeNationalDTO cooperativeNationalDTO
    ) throws URISyntaxException {
        log.debug("REST request to save CooperativeNational : {}", cooperativeNationalDTO);
        if (cooperativeNationalDTO.getId() != null) {
            throw new BadRequestAlertException("A new cooperativeNational cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CooperativeNationalDTO result = cooperativeNationalService.save(cooperativeNationalDTO);
        return ResponseEntity
            .created(new URI("/api/cooperative-nationals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cooperative-nationals/:id} : Updates an existing cooperativeNational.
     *
     * @param id the id of the cooperativeNationalDTO to save.
     * @param cooperativeNationalDTO the cooperativeNationalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cooperativeNationalDTO,
     * or with status {@code 400 (Bad Request)} if the cooperativeNationalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cooperativeNationalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cooperative-nationals/{id}")
    public ResponseEntity<CooperativeNationalDTO> updateCooperativeNational(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CooperativeNationalDTO cooperativeNationalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CooperativeNational : {}, {}", id, cooperativeNationalDTO);
        if (cooperativeNationalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cooperativeNationalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cooperativeNationalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CooperativeNationalDTO result = cooperativeNationalService.update(cooperativeNationalDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cooperativeNationalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cooperative-nationals/:id} : Partial updates given fields of an existing cooperativeNational, field will ignore if it is null
     *
     * @param id the id of the cooperativeNationalDTO to save.
     * @param cooperativeNationalDTO the cooperativeNationalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cooperativeNationalDTO,
     * or with status {@code 400 (Bad Request)} if the cooperativeNationalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cooperativeNationalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cooperativeNationalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cooperative-nationals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CooperativeNationalDTO> partialUpdateCooperativeNational(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CooperativeNationalDTO cooperativeNationalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CooperativeNational partially : {}, {}", id, cooperativeNationalDTO);
        if (cooperativeNationalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cooperativeNationalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cooperativeNationalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CooperativeNationalDTO> result = cooperativeNationalService.partialUpdate(cooperativeNationalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cooperativeNationalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cooperative-nationals} : get all the cooperativeNationals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cooperativeNationals in body.
     */
    @GetMapping("/cooperative-nationals")
    public ResponseEntity<List<CooperativeNationalDTO>> getAllCooperativeNationals(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CooperativeNationals");
        Page<CooperativeNationalDTO> page = cooperativeNationalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cooperative-nationals/:id} : get the "id" cooperativeNational.
     *
     * @param id the id of the cooperativeNationalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cooperativeNationalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cooperative-nationals/{id}")
    public ResponseEntity<CooperativeNationalDTO> getCooperativeNational(@PathVariable Long id) {
        log.debug("REST request to get CooperativeNational : {}", id);
        Optional<CooperativeNationalDTO> cooperativeNationalDTO = cooperativeNationalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cooperativeNationalDTO);
    }

    /**
     * {@code DELETE  /cooperative-nationals/:id} : delete the "id" cooperativeNational.
     *
     * @param id the id of the cooperativeNationalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cooperative-nationals/{id}")
    public ResponseEntity<Void> deleteCooperativeNational(@PathVariable Long id) {
        log.debug("REST request to delete CooperativeNational : {}", id);
        cooperativeNationalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
