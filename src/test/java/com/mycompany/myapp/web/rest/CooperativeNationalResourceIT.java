package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CooperativeLocal;
import com.mycompany.myapp.domain.CooperativeNational;
import com.mycompany.myapp.repository.CooperativeNationalRepository;
import com.mycompany.myapp.service.dto.CooperativeNationalDTO;
import com.mycompany.myapp.service.mapper.CooperativeNationalMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CooperativeNationalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CooperativeNationalResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FOURNISSEUR = "AAAAAAAAAA";
    private static final String UPDATED_FOURNISSEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cooperative-nationals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CooperativeNationalRepository cooperativeNationalRepository;

    @Autowired
    private CooperativeNationalMapper cooperativeNationalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCooperativeNationalMockMvc;

    private CooperativeNational cooperativeNational;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CooperativeNational createEntity(EntityManager em) {
        CooperativeNational cooperativeNational = new CooperativeNational().name(DEFAULT_NAME).fournisseur(DEFAULT_FOURNISSEUR);
        // Add required entity
        CooperativeLocal cooperativeLocal;
        if (TestUtil.findAll(em, CooperativeLocal.class).isEmpty()) {
            cooperativeLocal = CooperativeLocalResourceIT.createEntity(em);
            em.persist(cooperativeLocal);
            em.flush();
        } else {
            cooperativeLocal = TestUtil.findAll(em, CooperativeLocal.class).get(0);
        }
        cooperativeNational.getCoopLos().add(cooperativeLocal);
        return cooperativeNational;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CooperativeNational createUpdatedEntity(EntityManager em) {
        CooperativeNational cooperativeNational = new CooperativeNational().name(UPDATED_NAME).fournisseur(UPDATED_FOURNISSEUR);
        // Add required entity
        CooperativeLocal cooperativeLocal;
        if (TestUtil.findAll(em, CooperativeLocal.class).isEmpty()) {
            cooperativeLocal = CooperativeLocalResourceIT.createUpdatedEntity(em);
            em.persist(cooperativeLocal);
            em.flush();
        } else {
            cooperativeLocal = TestUtil.findAll(em, CooperativeLocal.class).get(0);
        }
        cooperativeNational.getCoopLos().add(cooperativeLocal);
        return cooperativeNational;
    }

    @BeforeEach
    public void initTest() {
        cooperativeNational = createEntity(em);
    }

    @Test
    @Transactional
    void createCooperativeNational() throws Exception {
        int databaseSizeBeforeCreate = cooperativeNationalRepository.findAll().size();
        // Create the CooperativeNational
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);
        restCooperativeNationalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeCreate + 1);
        CooperativeNational testCooperativeNational = cooperativeNationalList.get(cooperativeNationalList.size() - 1);
        assertThat(testCooperativeNational.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCooperativeNational.getFournisseur()).isEqualTo(DEFAULT_FOURNISSEUR);
    }

    @Test
    @Transactional
    void createCooperativeNationalWithExistingId() throws Exception {
        // Create the CooperativeNational with an existing ID
        cooperativeNational.setId(1L);
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        int databaseSizeBeforeCreate = cooperativeNationalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCooperativeNationalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperativeNationalRepository.findAll().size();
        // set the field null
        cooperativeNational.setName(null);

        // Create the CooperativeNational, which fails.
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        restCooperativeNationalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isBadRequest());

        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFournisseurIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperativeNationalRepository.findAll().size();
        // set the field null
        cooperativeNational.setFournisseur(null);

        // Create the CooperativeNational, which fails.
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        restCooperativeNationalMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isBadRequest());

        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCooperativeNationals() throws Exception {
        // Initialize the database
        cooperativeNationalRepository.saveAndFlush(cooperativeNational);

        // Get all the cooperativeNationalList
        restCooperativeNationalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cooperativeNational.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fournisseur").value(hasItem(DEFAULT_FOURNISSEUR)));
    }

    @Test
    @Transactional
    void getCooperativeNational() throws Exception {
        // Initialize the database
        cooperativeNationalRepository.saveAndFlush(cooperativeNational);

        // Get the cooperativeNational
        restCooperativeNationalMockMvc
            .perform(get(ENTITY_API_URL_ID, cooperativeNational.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cooperativeNational.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fournisseur").value(DEFAULT_FOURNISSEUR));
    }

    @Test
    @Transactional
    void getNonExistingCooperativeNational() throws Exception {
        // Get the cooperativeNational
        restCooperativeNationalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCooperativeNational() throws Exception {
        // Initialize the database
        cooperativeNationalRepository.saveAndFlush(cooperativeNational);

        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();

        // Update the cooperativeNational
        CooperativeNational updatedCooperativeNational = cooperativeNationalRepository.findById(cooperativeNational.getId()).get();
        // Disconnect from session so that the updates on updatedCooperativeNational are not directly saved in db
        em.detach(updatedCooperativeNational);
        updatedCooperativeNational.name(UPDATED_NAME).fournisseur(UPDATED_FOURNISSEUR);
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(updatedCooperativeNational);

        restCooperativeNationalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cooperativeNationalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isOk());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
        CooperativeNational testCooperativeNational = cooperativeNationalList.get(cooperativeNationalList.size() - 1);
        assertThat(testCooperativeNational.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCooperativeNational.getFournisseur()).isEqualTo(UPDATED_FOURNISSEUR);
    }

    @Test
    @Transactional
    void putNonExistingCooperativeNational() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();
        cooperativeNational.setId(count.incrementAndGet());

        // Create the CooperativeNational
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCooperativeNationalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cooperativeNationalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCooperativeNational() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();
        cooperativeNational.setId(count.incrementAndGet());

        // Create the CooperativeNational
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeNationalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCooperativeNational() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();
        cooperativeNational.setId(count.incrementAndGet());

        // Create the CooperativeNational
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeNationalMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCooperativeNationalWithPatch() throws Exception {
        // Initialize the database
        cooperativeNationalRepository.saveAndFlush(cooperativeNational);

        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();

        // Update the cooperativeNational using partial update
        CooperativeNational partialUpdatedCooperativeNational = new CooperativeNational();
        partialUpdatedCooperativeNational.setId(cooperativeNational.getId());

        partialUpdatedCooperativeNational.name(UPDATED_NAME);

        restCooperativeNationalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCooperativeNational.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCooperativeNational))
            )
            .andExpect(status().isOk());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
        CooperativeNational testCooperativeNational = cooperativeNationalList.get(cooperativeNationalList.size() - 1);
        assertThat(testCooperativeNational.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCooperativeNational.getFournisseur()).isEqualTo(DEFAULT_FOURNISSEUR);
    }

    @Test
    @Transactional
    void fullUpdateCooperativeNationalWithPatch() throws Exception {
        // Initialize the database
        cooperativeNationalRepository.saveAndFlush(cooperativeNational);

        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();

        // Update the cooperativeNational using partial update
        CooperativeNational partialUpdatedCooperativeNational = new CooperativeNational();
        partialUpdatedCooperativeNational.setId(cooperativeNational.getId());

        partialUpdatedCooperativeNational.name(UPDATED_NAME).fournisseur(UPDATED_FOURNISSEUR);

        restCooperativeNationalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCooperativeNational.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCooperativeNational))
            )
            .andExpect(status().isOk());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
        CooperativeNational testCooperativeNational = cooperativeNationalList.get(cooperativeNationalList.size() - 1);
        assertThat(testCooperativeNational.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCooperativeNational.getFournisseur()).isEqualTo(UPDATED_FOURNISSEUR);
    }

    @Test
    @Transactional
    void patchNonExistingCooperativeNational() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();
        cooperativeNational.setId(count.incrementAndGet());

        // Create the CooperativeNational
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCooperativeNationalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cooperativeNationalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCooperativeNational() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();
        cooperativeNational.setId(count.incrementAndGet());

        // Create the CooperativeNational
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeNationalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCooperativeNational() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeNationalRepository.findAll().size();
        cooperativeNational.setId(count.incrementAndGet());

        // Create the CooperativeNational
        CooperativeNationalDTO cooperativeNationalDTO = cooperativeNationalMapper.toDto(cooperativeNational);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeNationalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeNationalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CooperativeNational in the database
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCooperativeNational() throws Exception {
        // Initialize the database
        cooperativeNationalRepository.saveAndFlush(cooperativeNational);

        int databaseSizeBeforeDelete = cooperativeNationalRepository.findAll().size();

        // Delete the cooperativeNational
        restCooperativeNationalMockMvc
            .perform(delete(ENTITY_API_URL_ID, cooperativeNational.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CooperativeNational> cooperativeNationalList = cooperativeNationalRepository.findAll();
        assertThat(cooperativeNationalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
