package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.ConseilRepository;
import com.mycompany.myapp.service.ConseilService;
import com.mycompany.myapp.service.dto.ConseilDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Conseil}.
 */
@RestController
@RequestMapping("/api")
public class ConseilResource {

    private final Logger log = LoggerFactory.getLogger(ConseilResource.class);

    private static final String ENTITY_NAME = "conseil";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConseilService conseilService;

    private final ConseilRepository conseilRepository;

    public ConseilResource(ConseilService conseilService, ConseilRepository conseilRepository) {
        this.conseilService = conseilService;
        this.conseilRepository = conseilRepository;
    }

    /**
     * {@code POST  /conseils} : Create a new conseil.
     *
     * @param conseilDTO the conseilDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new conseilDTO, or with status {@code 400 (Bad Request)} if the conseil has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/conseils")
    public ResponseEntity<ConseilDTO> createConseil(@Valid @RequestBody ConseilDTO conseilDTO) throws URISyntaxException {
        log.debug("REST request to save Conseil : {}", conseilDTO);
        if (conseilDTO.getId() != null) {
            throw new BadRequestAlertException("A new conseil cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConseilDTO result = conseilService.save(conseilDTO);
        return ResponseEntity
            .created(new URI("/api/conseils/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /conseils/:id} : Updates an existing conseil.
     *
     * @param id the id of the conseilDTO to save.
     * @param conseilDTO the conseilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conseilDTO,
     * or with status {@code 400 (Bad Request)} if the conseilDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the conseilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/conseils/{id}")
    public ResponseEntity<ConseilDTO> updateConseil(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConseilDTO conseilDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Conseil : {}, {}", id, conseilDTO);
        if (conseilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conseilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conseilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConseilDTO result = conseilService.update(conseilDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conseilDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /conseils/:id} : Partial updates given fields of an existing conseil, field will ignore if it is null
     *
     * @param id the id of the conseilDTO to save.
     * @param conseilDTO the conseilDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated conseilDTO,
     * or with status {@code 400 (Bad Request)} if the conseilDTO is not valid,
     * or with status {@code 404 (Not Found)} if the conseilDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the conseilDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/conseils/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConseilDTO> partialUpdateConseil(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConseilDTO conseilDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Conseil partially : {}, {}", id, conseilDTO);
        if (conseilDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, conseilDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!conseilRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConseilDTO> result = conseilService.partialUpdate(conseilDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, conseilDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /conseils} : get all the conseils.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of conseils in body.
     */
    @GetMapping("/conseils")
    public ResponseEntity<List<ConseilDTO>> getAllConseils(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Conseils");
        Page<ConseilDTO> page = conseilService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /conseils/:id} : get the "id" conseil.
     *
     * @param id the id of the conseilDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the conseilDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/conseils/{id}")
    public ResponseEntity<ConseilDTO> getConseil(@PathVariable Long id) {
        log.debug("REST request to get Conseil : {}", id);
        Optional<ConseilDTO> conseilDTO = conseilService.findOne(id);
        return ResponseUtil.wrapOrNotFound(conseilDTO);
    }

    /**
     * {@code DELETE  /conseils/:id} : delete the "id" conseil.
     *
     * @param id the id of the conseilDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/conseils/{id}")
    public ResponseEntity<Void> deleteConseil(@PathVariable Long id) {
        log.debug("REST request to delete Conseil : {}", id);
        conseilService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
