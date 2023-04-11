package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.CooperativeLocalRepository;
import com.mycompany.myapp.service.CooperativeLocalService;
import com.mycompany.myapp.service.dto.CooperativeLocalDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.CooperativeLocal}.
 */
@RestController
@RequestMapping("/api")
public class CooperativeLocalResource {

    private final Logger log = LoggerFactory.getLogger(CooperativeLocalResource.class);

    private static final String ENTITY_NAME = "cooperativeLocal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CooperativeLocalService cooperativeLocalService;

    private final CooperativeLocalRepository cooperativeLocalRepository;

    public CooperativeLocalResource(
        CooperativeLocalService cooperativeLocalService,
        CooperativeLocalRepository cooperativeLocalRepository
    ) {
        this.cooperativeLocalService = cooperativeLocalService;
        this.cooperativeLocalRepository = cooperativeLocalRepository;
    }

    /**
     * {@code POST  /cooperative-locals} : Create a new cooperativeLocal.
     *
     * @param cooperativeLocalDTO the cooperativeLocalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cooperativeLocalDTO, or with status {@code 400 (Bad Request)} if the cooperativeLocal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cooperative-locals")
    public ResponseEntity<CooperativeLocalDTO> createCooperativeLocal(@Valid @RequestBody CooperativeLocalDTO cooperativeLocalDTO)
        throws URISyntaxException {
        log.debug("REST request to save CooperativeLocal : {}", cooperativeLocalDTO);
        if (cooperativeLocalDTO.getId() != null) {
            throw new BadRequestAlertException("A new cooperativeLocal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CooperativeLocalDTO result = cooperativeLocalService.save(cooperativeLocalDTO);
        return ResponseEntity
            .created(new URI("/api/cooperative-locals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cooperative-locals/:id} : Updates an existing cooperativeLocal.
     *
     * @param id the id of the cooperativeLocalDTO to save.
     * @param cooperativeLocalDTO the cooperativeLocalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cooperativeLocalDTO,
     * or with status {@code 400 (Bad Request)} if the cooperativeLocalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cooperativeLocalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cooperative-locals/{id}")
    public ResponseEntity<CooperativeLocalDTO> updateCooperativeLocal(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CooperativeLocalDTO cooperativeLocalDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CooperativeLocal : {}, {}", id, cooperativeLocalDTO);
        if (cooperativeLocalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cooperativeLocalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cooperativeLocalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CooperativeLocalDTO result = cooperativeLocalService.update(cooperativeLocalDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cooperativeLocalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cooperative-locals/:id} : Partial updates given fields of an existing cooperativeLocal, field will ignore if it is null
     *
     * @param id the id of the cooperativeLocalDTO to save.
     * @param cooperativeLocalDTO the cooperativeLocalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cooperativeLocalDTO,
     * or with status {@code 400 (Bad Request)} if the cooperativeLocalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the cooperativeLocalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the cooperativeLocalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cooperative-locals/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CooperativeLocalDTO> partialUpdateCooperativeLocal(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CooperativeLocalDTO cooperativeLocalDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CooperativeLocal partially : {}, {}", id, cooperativeLocalDTO);
        if (cooperativeLocalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, cooperativeLocalDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cooperativeLocalRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CooperativeLocalDTO> result = cooperativeLocalService.partialUpdate(cooperativeLocalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cooperativeLocalDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cooperative-locals} : get all the cooperativeLocals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cooperativeLocals in body.
     */
    @GetMapping("/cooperative-locals")
    public ResponseEntity<List<CooperativeLocalDTO>> getAllCooperativeLocals(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CooperativeLocals");
        Page<CooperativeLocalDTO> page = cooperativeLocalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cooperative-locals/:id} : get the "id" cooperativeLocal.
     *
     * @param id the id of the cooperativeLocalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cooperativeLocalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cooperative-locals/{id}")
    public ResponseEntity<CooperativeLocalDTO> getCooperativeLocal(@PathVariable Long id) {
        log.debug("REST request to get CooperativeLocal : {}", id);
        Optional<CooperativeLocalDTO> cooperativeLocalDTO = cooperativeLocalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cooperativeLocalDTO);
    }

    /**
     * {@code DELETE  /cooperative-locals/:id} : delete the "id" cooperativeLocal.
     *
     * @param id the id of the cooperativeLocalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cooperative-locals/{id}")
    public ResponseEntity<Void> deleteCooperativeLocal(@PathVariable Long id) {
        log.debug("REST request to delete CooperativeLocal : {}", id);
        cooperativeLocalService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
