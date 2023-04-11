package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Restorateur;
import com.mycompany.myapp.repository.RestorateurRepository;
import com.mycompany.myapp.service.dto.RestorateurDTO;
import com.mycompany.myapp.service.mapper.RestorateurMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Restorateur}.
 */
@Service
@Transactional
public class RestorateurService {

    private final Logger log = LoggerFactory.getLogger(RestorateurService.class);

    private final RestorateurRepository restorateurRepository;

    private final RestorateurMapper restorateurMapper;

    public RestorateurService(RestorateurRepository restorateurRepository, RestorateurMapper restorateurMapper) {
        this.restorateurRepository = restorateurRepository;
        this.restorateurMapper = restorateurMapper;
    }

    /**
     * Save a restorateur.
     *
     * @param restorateurDTO the entity to save.
     * @return the persisted entity.
     */
    public RestorateurDTO save(RestorateurDTO restorateurDTO) {
        log.debug("Request to save Restorateur : {}", restorateurDTO);
        Restorateur restorateur = restorateurMapper.toEntity(restorateurDTO);
        restorateur = restorateurRepository.save(restorateur);
        return restorateurMapper.toDto(restorateur);
    }

    /**
     * Update a restorateur.
     *
     * @param restorateurDTO the entity to save.
     * @return the persisted entity.
     */
    public RestorateurDTO update(RestorateurDTO restorateurDTO) {
        log.debug("Request to update Restorateur : {}", restorateurDTO);
        Restorateur restorateur = restorateurMapper.toEntity(restorateurDTO);
        restorateur = restorateurRepository.save(restorateur);
        return restorateurMapper.toDto(restorateur);
    }

    /**
     * Partially update a restorateur.
     *
     * @param restorateurDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RestorateurDTO> partialUpdate(RestorateurDTO restorateurDTO) {
        log.debug("Request to partially update Restorateur : {}", restorateurDTO);

        return restorateurRepository
            .findById(restorateurDTO.getId())
            .map(existingRestorateur -> {
                restorateurMapper.partialUpdate(existingRestorateur, restorateurDTO);

                return existingRestorateur;
            })
            .map(restorateurRepository::save)
            .map(restorateurMapper::toDto);
    }

    /**
     * Get all the restorateurs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<RestorateurDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Restorateurs");
        return restorateurRepository.findAll(pageable).map(restorateurMapper::toDto);
    }

    /**
     * Get one restorateur by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<RestorateurDTO> findOne(Long id) {
        log.debug("Request to get Restorateur : {}", id);
        return restorateurRepository.findById(id).map(restorateurMapper::toDto);
    }

    /**
     * Delete the restorateur by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Restorateur : {}", id);
        restorateurRepository.deleteById(id);
    }
}
