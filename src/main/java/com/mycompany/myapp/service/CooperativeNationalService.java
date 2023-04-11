package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CooperativeNational;
import com.mycompany.myapp.repository.CooperativeNationalRepository;
import com.mycompany.myapp.service.dto.CooperativeNationalDTO;
import com.mycompany.myapp.service.mapper.CooperativeNationalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CooperativeNational}.
 */
@Service
@Transactional
public class CooperativeNationalService {

    private final Logger log = LoggerFactory.getLogger(CooperativeNationalService.class);

    private final CooperativeNationalRepository cooperativeNationalRepository;

    private final CooperativeNationalMapper cooperativeNationalMapper;

    public CooperativeNationalService(
        CooperativeNationalRepository cooperativeNationalRepository,
        CooperativeNationalMapper cooperativeNationalMapper
    ) {
        this.cooperativeNationalRepository = cooperativeNationalRepository;
        this.cooperativeNationalMapper = cooperativeNationalMapper;
    }

    /**
     * Save a cooperativeNational.
     *
     * @param cooperativeNationalDTO the entity to save.
     * @return the persisted entity.
     */
    public CooperativeNationalDTO save(CooperativeNationalDTO cooperativeNationalDTO) {
        log.debug("Request to save CooperativeNational : {}", cooperativeNationalDTO);
        CooperativeNational cooperativeNational = cooperativeNationalMapper.toEntity(cooperativeNationalDTO);
        cooperativeNational = cooperativeNationalRepository.save(cooperativeNational);
        return cooperativeNationalMapper.toDto(cooperativeNational);
    }

    /**
     * Update a cooperativeNational.
     *
     * @param cooperativeNationalDTO the entity to save.
     * @return the persisted entity.
     */
    public CooperativeNationalDTO update(CooperativeNationalDTO cooperativeNationalDTO) {
        log.debug("Request to update CooperativeNational : {}", cooperativeNationalDTO);
        CooperativeNational cooperativeNational = cooperativeNationalMapper.toEntity(cooperativeNationalDTO);
        cooperativeNational = cooperativeNationalRepository.save(cooperativeNational);
        return cooperativeNationalMapper.toDto(cooperativeNational);
    }

    /**
     * Partially update a cooperativeNational.
     *
     * @param cooperativeNationalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CooperativeNationalDTO> partialUpdate(CooperativeNationalDTO cooperativeNationalDTO) {
        log.debug("Request to partially update CooperativeNational : {}", cooperativeNationalDTO);

        return cooperativeNationalRepository
            .findById(cooperativeNationalDTO.getId())
            .map(existingCooperativeNational -> {
                cooperativeNationalMapper.partialUpdate(existingCooperativeNational, cooperativeNationalDTO);

                return existingCooperativeNational;
            })
            .map(cooperativeNationalRepository::save)
            .map(cooperativeNationalMapper::toDto);
    }

    /**
     * Get all the cooperativeNationals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CooperativeNationalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CooperativeNationals");
        return cooperativeNationalRepository.findAll(pageable).map(cooperativeNationalMapper::toDto);
    }

    /**
     * Get one cooperativeNational by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CooperativeNationalDTO> findOne(Long id) {
        log.debug("Request to get CooperativeNational : {}", id);
        return cooperativeNationalRepository.findById(id).map(cooperativeNationalMapper::toDto);
    }

    /**
     * Delete the cooperativeNational by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CooperativeNational : {}", id);
        cooperativeNationalRepository.deleteById(id);
    }
}
