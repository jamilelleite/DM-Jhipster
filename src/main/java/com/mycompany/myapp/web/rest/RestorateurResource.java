package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.RestorateurRepository;
import com.mycompany.myapp.service.RestorateurService;
import com.mycompany.myapp.service.dto.RestorateurDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Restorateur}.
 */
@RestController
@RequestMapping("/api")
public class RestorateurResource {

    private final Logger log = LoggerFactory.getLogger(RestorateurResource.class);

    private static final String ENTITY_NAME = "restorateur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestorateurService restorateurService;

    private final RestorateurRepository restorateurRepository;

    public RestorateurResource(RestorateurService restorateurService, RestorateurRepository restorateurRepository) {
        this.restorateurService = restorateurService;
        this.restorateurRepository = restorateurRepository;
    }

    /**
     * {@code POST  /restorateurs} : Create a new restorateur.
     *
     * @param restorateurDTO the restorateurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restorateurDTO, or with status {@code 400 (Bad Request)} if the restorateur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/restorateurs")
    public ResponseEntity<RestorateurDTO> createRestorateur(@Valid @RequestBody RestorateurDTO restorateurDTO) throws URISyntaxException {
        log.debug("REST request to save Restorateur : {}", restorateurDTO);
        if (restorateurDTO.getId() != null) {
            throw new BadRequestAlertException("A new restorateur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RestorateurDTO result = restorateurService.save(restorateurDTO);
        return ResponseEntity
            .created(new URI("/api/restorateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /restorateurs/:id} : Updates an existing restorateur.
     *
     * @param id the id of the restorateurDTO to save.
     * @param restorateurDTO the restorateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restorateurDTO,
     * or with status {@code 400 (Bad Request)} if the restorateurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restorateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/restorateurs/{id}")
    public ResponseEntity<RestorateurDTO> updateRestorateur(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RestorateurDTO restorateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Restorateur : {}, {}", id, restorateurDTO);
        if (restorateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restorateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restorateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RestorateurDTO result = restorateurService.update(restorateurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restorateurDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /restorateurs/:id} : Partial updates given fields of an existing restorateur, field will ignore if it is null
     *
     * @param id the id of the restorateurDTO to save.
     * @param restorateurDTO the restorateurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restorateurDTO,
     * or with status {@code 400 (Bad Request)} if the restorateurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the restorateurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the restorateurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/restorateurs/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RestorateurDTO> partialUpdateRestorateur(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RestorateurDTO restorateurDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Restorateur partially : {}, {}", id, restorateurDTO);
        if (restorateurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, restorateurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!restorateurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RestorateurDTO> result = restorateurService.partialUpdate(restorateurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restorateurDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /restorateurs} : get all the restorateurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restorateurs in body.
     */
    @GetMapping("/restorateurs")
    public ResponseEntity<List<RestorateurDTO>> getAllRestorateurs(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Restorateurs");
        Page<RestorateurDTO> page = restorateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /restorateurs/:id} : get the "id" restorateur.
     *
     * @param id the id of the restorateurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restorateurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/restorateurs/{id}")
    public ResponseEntity<RestorateurDTO> getRestorateur(@PathVariable Long id) {
        log.debug("REST request to get Restorateur : {}", id);
        Optional<RestorateurDTO> restorateurDTO = restorateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restorateurDTO);
    }

    /**
     * {@code DELETE  /restorateurs/:id} : delete the "id" restorateur.
     *
     * @param id the id of the restorateurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/restorateurs/{id}")
    public ResponseEntity<Void> deleteRestorateur(@PathVariable Long id) {
        log.debug("REST request to delete Restorateur : {}", id);
        restorateurService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
