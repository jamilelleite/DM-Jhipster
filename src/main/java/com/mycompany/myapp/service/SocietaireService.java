package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.repository.SocietaireRepository;
import com.mycompany.myapp.service.dto.SocietaireDTO;
import com.mycompany.myapp.service.mapper.SocietaireMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Societaire}.
 */
@Service
@Transactional
public class SocietaireService {

    private final Logger log = LoggerFactory.getLogger(SocietaireService.class);

    private final SocietaireRepository societaireRepository;

    private final SocietaireMapper societaireMapper;

    public SocietaireService(SocietaireRepository societaireRepository, SocietaireMapper societaireMapper) {
        this.societaireRepository = societaireRepository;
        this.societaireMapper = societaireMapper;
    }

    /**
     * Save a societaire.
     *
     * @param societaireDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietaireDTO save(SocietaireDTO societaireDTO) {
        log.debug("Request to save Societaire : {}", societaireDTO);
        Societaire societaire = societaireMapper.toEntity(societaireDTO);
        societaire = societaireRepository.save(societaire);
        return societaireMapper.toDto(societaire);
    }

    /**
     * Update a societaire.
     *
     * @param societaireDTO the entity to save.
     * @return the persisted entity.
     */
    public SocietaireDTO update(SocietaireDTO societaireDTO) {
        log.debug("Request to update Societaire : {}", societaireDTO);
        Societaire societaire = societaireMapper.toEntity(societaireDTO);
        societaire = societaireRepository.save(societaire);
        return societaireMapper.toDto(societaire);
    }

    /**
     * Partially update a societaire.
     *
     * @param societaireDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SocietaireDTO> partialUpdate(SocietaireDTO societaireDTO) {
        log.debug("Request to partially update Societaire : {}", societaireDTO);

        return societaireRepository
            .findById(societaireDTO.getId())
            .map(existingSocietaire -> {
                societaireMapper.partialUpdate(existingSocietaire, societaireDTO);

                return existingSocietaire;
            })
            .map(societaireRepository::save)
            .map(societaireMapper::toDto);
    }

    /**
     * Get all the societaires.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SocietaireDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Societaires");
        return societaireRepository.findAll(pageable).map(societaireMapper::toDto);
    }

    /**
     * Get one societaire by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SocietaireDTO> findOne(Long id) {
        log.debug("Request to get Societaire : {}", id);
        return societaireRepository.findById(id).map(societaireMapper::toDto);
    }

    /**
     * Delete the societaire by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Societaire : {}", id);
        societaireRepository.deleteById(id);
    }
}
