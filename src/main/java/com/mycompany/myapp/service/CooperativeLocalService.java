package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.CooperativeLocal;
import com.mycompany.myapp.repository.CooperativeLocalRepository;
import com.mycompany.myapp.service.dto.CooperativeLocalDTO;
import com.mycompany.myapp.service.mapper.CooperativeLocalMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CooperativeLocal}.
 */
@Service
@Transactional
public class CooperativeLocalService {

    private final Logger log = LoggerFactory.getLogger(CooperativeLocalService.class);

    private final CooperativeLocalRepository cooperativeLocalRepository;

    private final CooperativeLocalMapper cooperativeLocalMapper;

    public CooperativeLocalService(CooperativeLocalRepository cooperativeLocalRepository, CooperativeLocalMapper cooperativeLocalMapper) {
        this.cooperativeLocalRepository = cooperativeLocalRepository;
        this.cooperativeLocalMapper = cooperativeLocalMapper;
    }

    /**
     * Save a cooperativeLocal.
     *
     * @param cooperativeLocalDTO the entity to save.
     * @return the persisted entity.
     */
    public CooperativeLocalDTO save(CooperativeLocalDTO cooperativeLocalDTO) {
        log.debug("Request to save CooperativeLocal : {}", cooperativeLocalDTO);
        CooperativeLocal cooperativeLocal = cooperativeLocalMapper.toEntity(cooperativeLocalDTO);
        cooperativeLocal = cooperativeLocalRepository.save(cooperativeLocal);
        return cooperativeLocalMapper.toDto(cooperativeLocal);
    }

    /**
     * Update a cooperativeLocal.
     *
     * @param cooperativeLocalDTO the entity to save.
     * @return the persisted entity.
     */
    public CooperativeLocalDTO update(CooperativeLocalDTO cooperativeLocalDTO) {
        log.debug("Request to update CooperativeLocal : {}", cooperativeLocalDTO);
        CooperativeLocal cooperativeLocal = cooperativeLocalMapper.toEntity(cooperativeLocalDTO);
        cooperativeLocal = cooperativeLocalRepository.save(cooperativeLocal);
        return cooperativeLocalMapper.toDto(cooperativeLocal);
    }

    /**
     * Partially update a cooperativeLocal.
     *
     * @param cooperativeLocalDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CooperativeLocalDTO> partialUpdate(CooperativeLocalDTO cooperativeLocalDTO) {
        log.debug("Request to partially update CooperativeLocal : {}", cooperativeLocalDTO);

        return cooperativeLocalRepository
            .findById(cooperativeLocalDTO.getId())
            .map(existingCooperativeLocal -> {
                cooperativeLocalMapper.partialUpdate(existingCooperativeLocal, cooperativeLocalDTO);

                return existingCooperativeLocal;
            })
            .map(cooperativeLocalRepository::save)
            .map(cooperativeLocalMapper::toDto);
    }

    /**
     * Get all the cooperativeLocals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CooperativeLocalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CooperativeLocals");
        return cooperativeLocalRepository.findAll(pageable).map(cooperativeLocalMapper::toDto);
    }

    /**
     * Get one cooperativeLocal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CooperativeLocalDTO> findOne(Long id) {
        log.debug("Request to get CooperativeLocal : {}", id);
        return cooperativeLocalRepository.findById(id).map(cooperativeLocalMapper::toDto);
    }

    /**
     * Delete the cooperativeLocal by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CooperativeLocal : {}", id);
        cooperativeLocalRepository.deleteById(id);
    }
}
