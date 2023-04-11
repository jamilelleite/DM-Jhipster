package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Conseil;
import com.mycompany.myapp.repository.ConseilRepository;
import com.mycompany.myapp.service.dto.ConseilDTO;
import com.mycompany.myapp.service.mapper.ConseilMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Conseil}.
 */
@Service
@Transactional
public class ConseilService {

    private final Logger log = LoggerFactory.getLogger(ConseilService.class);

    private final ConseilRepository conseilRepository;

    private final ConseilMapper conseilMapper;

    public ConseilService(ConseilRepository conseilRepository, ConseilMapper conseilMapper) {
        this.conseilRepository = conseilRepository;
        this.conseilMapper = conseilMapper;
    }

    /**
     * Save a conseil.
     *
     * @param conseilDTO the entity to save.
     * @return the persisted entity.
     */
    public ConseilDTO save(ConseilDTO conseilDTO) {
        log.debug("Request to save Conseil : {}", conseilDTO);
        Conseil conseil = conseilMapper.toEntity(conseilDTO);
        conseil = conseilRepository.save(conseil);
        return conseilMapper.toDto(conseil);
    }

    /**
     * Update a conseil.
     *
     * @param conseilDTO the entity to save.
     * @return the persisted entity.
     */
    public ConseilDTO update(ConseilDTO conseilDTO) {
        log.debug("Request to update Conseil : {}", conseilDTO);
        Conseil conseil = conseilMapper.toEntity(conseilDTO);
        conseil = conseilRepository.save(conseil);
        return conseilMapper.toDto(conseil);
    }

    /**
     * Partially update a conseil.
     *
     * @param conseilDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConseilDTO> partialUpdate(ConseilDTO conseilDTO) {
        log.debug("Request to partially update Conseil : {}", conseilDTO);

        return conseilRepository
            .findById(conseilDTO.getId())
            .map(existingConseil -> {
                conseilMapper.partialUpdate(existingConseil, conseilDTO);

                return existingConseil;
            })
            .map(conseilRepository::save)
            .map(conseilMapper::toDto);
    }

    /**
     * Get all the conseils.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConseilDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Conseils");
        return conseilRepository.findAll(pageable).map(conseilMapper::toDto);
    }

    /**
     * Get one conseil by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConseilDTO> findOne(Long id) {
        log.debug("Request to get Conseil : {}", id);
        return conseilRepository.findById(id).map(conseilMapper::toDto);
    }

    /**
     * Delete the conseil by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Conseil : {}", id);
        conseilRepository.deleteById(id);
    }
}
