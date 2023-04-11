package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.SocietaireRepository;
import com.mycompany.myapp.service.SocietaireService;
import com.mycompany.myapp.service.dto.SocietaireDTO;
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
 * REST controller for managing {@link com.mycompany.myapp.domain.Societaire}.
 */
@RestController
@RequestMapping("/api")
public class SocietaireResource {

    private final Logger log = LoggerFactory.getLogger(SocietaireResource.class);

    private static final String ENTITY_NAME = "societaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocietaireService societaireService;

    private final SocietaireRepository societaireRepository;

    public SocietaireResource(SocietaireService societaireService, SocietaireRepository societaireRepository) {
        this.societaireService = societaireService;
        this.societaireRepository = societaireRepository;
    }

    /**
     * {@code POST  /societaires} : Create a new societaire.
     *
     * @param societaireDTO the societaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new societaireDTO, or with status {@code 400 (Bad Request)} if the societaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/societaires")
    public ResponseEntity<SocietaireDTO> createSocietaire(@Valid @RequestBody SocietaireDTO societaireDTO) throws URISyntaxException {
        log.debug("REST request to save Societaire : {}", societaireDTO);
        if (societaireDTO.getId() != null) {
            throw new BadRequestAlertException("A new societaire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocietaireDTO result = societaireService.save(societaireDTO);
        return ResponseEntity
            .created(new URI("/api/societaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /societaires/:id} : Updates an existing societaire.
     *
     * @param id the id of the societaireDTO to save.
     * @param societaireDTO the societaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societaireDTO,
     * or with status {@code 400 (Bad Request)} if the societaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the societaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/societaires/{id}")
    public ResponseEntity<SocietaireDTO> updateSocietaire(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SocietaireDTO societaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Societaire : {}, {}", id, societaireDTO);
        if (societaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocietaireDTO result = societaireService.update(societaireDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societaireDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /societaires/:id} : Partial updates given fields of an existing societaire, field will ignore if it is null
     *
     * @param id the id of the societaireDTO to save.
     * @param societaireDTO the societaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated societaireDTO,
     * or with status {@code 400 (Bad Request)} if the societaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the societaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the societaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/societaires/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocietaireDTO> partialUpdateSocietaire(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SocietaireDTO societaireDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Societaire partially : {}, {}", id, societaireDTO);
        if (societaireDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, societaireDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!societaireRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocietaireDTO> result = societaireService.partialUpdate(societaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, societaireDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /societaires} : get all the societaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of societaires in body.
     */
    @GetMapping("/societaires")
    public ResponseEntity<List<SocietaireDTO>> getAllSocietaires(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Societaires");
        Page<SocietaireDTO> page = societaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /societaires/:id} : get the "id" societaire.
     *
     * @param id the id of the societaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the societaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/societaires/{id}")
    public ResponseEntity<SocietaireDTO> getSocietaire(@PathVariable Long id) {
        log.debug("REST request to get Societaire : {}", id);
        Optional<SocietaireDTO> societaireDTO = societaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(societaireDTO);
    }

    /**
     * {@code DELETE  /societaires/:id} : delete the "id" societaire.
     *
     * @param id the id of the societaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/societaires/{id}")
    public ResponseEntity<Void> deleteSocietaire(@PathVariable Long id) {
        log.debug("REST request to delete Societaire : {}", id);
        societaireService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
