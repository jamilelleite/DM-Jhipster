package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.CooperativeLocal;
import com.mycompany.myapp.domain.CooperativeNational;
import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.repository.CooperativeLocalRepository;
import com.mycompany.myapp.service.dto.CooperativeLocalDTO;
import com.mycompany.myapp.service.mapper.CooperativeLocalMapper;
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
 * Integration tests for the {@link CooperativeLocalResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CooperativeLocalResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_ZONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cooperative-locals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CooperativeLocalRepository cooperativeLocalRepository;

    @Autowired
    private CooperativeLocalMapper cooperativeLocalMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCooperativeLocalMockMvc;

    private CooperativeLocal cooperativeLocal;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CooperativeLocal createEntity(EntityManager em) {
        CooperativeLocal cooperativeLocal = new CooperativeLocal().name(DEFAULT_NAME).zone(DEFAULT_ZONE);
        // Add required entity
        CooperativeNational cooperativeNational;
        if (TestUtil.findAll(em, CooperativeNational.class).isEmpty()) {
            cooperativeNational = CooperativeNationalResourceIT.createEntity(em);
            em.persist(cooperativeNational);
            em.flush();
        } else {
            cooperativeNational = TestUtil.findAll(em, CooperativeNational.class).get(0);
        }
        cooperativeLocal.setCoopNaname(cooperativeNational);
        // Add required entity
        Societaire societaire;
        if (TestUtil.findAll(em, Societaire.class).isEmpty()) {
            societaire = SocietaireResourceIT.createEntity(em);
            em.persist(societaire);
            em.flush();
        } else {
            societaire = TestUtil.findAll(em, Societaire.class).get(0);
        }
        cooperativeLocal.getSocnames().add(societaire);
        return cooperativeLocal;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CooperativeLocal createUpdatedEntity(EntityManager em) {
        CooperativeLocal cooperativeLocal = new CooperativeLocal().name(UPDATED_NAME).zone(UPDATED_ZONE);
        // Add required entity
        CooperativeNational cooperativeNational;
        if (TestUtil.findAll(em, CooperativeNational.class).isEmpty()) {
            cooperativeNational = CooperativeNationalResourceIT.createUpdatedEntity(em);
            em.persist(cooperativeNational);
            em.flush();
        } else {
            cooperativeNational = TestUtil.findAll(em, CooperativeNational.class).get(0);
        }
        cooperativeLocal.setCoopNaname(cooperativeNational);
        // Add required entity
        Societaire societaire;
        if (TestUtil.findAll(em, Societaire.class).isEmpty()) {
            societaire = SocietaireResourceIT.createUpdatedEntity(em);
            em.persist(societaire);
            em.flush();
        } else {
            societaire = TestUtil.findAll(em, Societaire.class).get(0);
        }
        cooperativeLocal.getSocnames().add(societaire);
        return cooperativeLocal;
    }

    @BeforeEach
    public void initTest() {
        cooperativeLocal = createEntity(em);
    }

    @Test
    @Transactional
    void createCooperativeLocal() throws Exception {
        int databaseSizeBeforeCreate = cooperativeLocalRepository.findAll().size();
        // Create the CooperativeLocal
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);
        restCooperativeLocalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeCreate + 1);
        CooperativeLocal testCooperativeLocal = cooperativeLocalList.get(cooperativeLocalList.size() - 1);
        assertThat(testCooperativeLocal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCooperativeLocal.getZone()).isEqualTo(DEFAULT_ZONE);
    }

    @Test
    @Transactional
    void createCooperativeLocalWithExistingId() throws Exception {
        // Create the CooperativeLocal with an existing ID
        cooperativeLocal.setId(1L);
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        int databaseSizeBeforeCreate = cooperativeLocalRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCooperativeLocalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperativeLocalRepository.findAll().size();
        // set the field null
        cooperativeLocal.setName(null);

        // Create the CooperativeLocal, which fails.
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        restCooperativeLocalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isBadRequest());

        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkZoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = cooperativeLocalRepository.findAll().size();
        // set the field null
        cooperativeLocal.setZone(null);

        // Create the CooperativeLocal, which fails.
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        restCooperativeLocalMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isBadRequest());

        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCooperativeLocals() throws Exception {
        // Initialize the database
        cooperativeLocalRepository.saveAndFlush(cooperativeLocal);

        // Get all the cooperativeLocalList
        restCooperativeLocalMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cooperativeLocal.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)));
    }

    @Test
    @Transactional
    void getCooperativeLocal() throws Exception {
        // Initialize the database
        cooperativeLocalRepository.saveAndFlush(cooperativeLocal);

        // Get the cooperativeLocal
        restCooperativeLocalMockMvc
            .perform(get(ENTITY_API_URL_ID, cooperativeLocal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cooperativeLocal.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE));
    }

    @Test
    @Transactional
    void getNonExistingCooperativeLocal() throws Exception {
        // Get the cooperativeLocal
        restCooperativeLocalMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCooperativeLocal() throws Exception {
        // Initialize the database
        cooperativeLocalRepository.saveAndFlush(cooperativeLocal);

        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();

        // Update the cooperativeLocal
        CooperativeLocal updatedCooperativeLocal = cooperativeLocalRepository.findById(cooperativeLocal.getId()).get();
        // Disconnect from session so that the updates on updatedCooperativeLocal are not directly saved in db
        em.detach(updatedCooperativeLocal);
        updatedCooperativeLocal.name(UPDATED_NAME).zone(UPDATED_ZONE);
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(updatedCooperativeLocal);

        restCooperativeLocalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cooperativeLocalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isOk());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
        CooperativeLocal testCooperativeLocal = cooperativeLocalList.get(cooperativeLocalList.size() - 1);
        assertThat(testCooperativeLocal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCooperativeLocal.getZone()).isEqualTo(UPDATED_ZONE);
    }

    @Test
    @Transactional
    void putNonExistingCooperativeLocal() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();
        cooperativeLocal.setId(count.incrementAndGet());

        // Create the CooperativeLocal
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCooperativeLocalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cooperativeLocalDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCooperativeLocal() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();
        cooperativeLocal.setId(count.incrementAndGet());

        // Create the CooperativeLocal
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeLocalMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCooperativeLocal() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();
        cooperativeLocal.setId(count.incrementAndGet());

        // Create the CooperativeLocal
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeLocalMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCooperativeLocalWithPatch() throws Exception {
        // Initialize the database
        cooperativeLocalRepository.saveAndFlush(cooperativeLocal);

        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();

        // Update the cooperativeLocal using partial update
        CooperativeLocal partialUpdatedCooperativeLocal = new CooperativeLocal();
        partialUpdatedCooperativeLocal.setId(cooperativeLocal.getId());

        partialUpdatedCooperativeLocal.zone(UPDATED_ZONE);

        restCooperativeLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCooperativeLocal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCooperativeLocal))
            )
            .andExpect(status().isOk());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
        CooperativeLocal testCooperativeLocal = cooperativeLocalList.get(cooperativeLocalList.size() - 1);
        assertThat(testCooperativeLocal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCooperativeLocal.getZone()).isEqualTo(UPDATED_ZONE);
    }

    @Test
    @Transactional
    void fullUpdateCooperativeLocalWithPatch() throws Exception {
        // Initialize the database
        cooperativeLocalRepository.saveAndFlush(cooperativeLocal);

        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();

        // Update the cooperativeLocal using partial update
        CooperativeLocal partialUpdatedCooperativeLocal = new CooperativeLocal();
        partialUpdatedCooperativeLocal.setId(cooperativeLocal.getId());

        partialUpdatedCooperativeLocal.name(UPDATED_NAME).zone(UPDATED_ZONE);

        restCooperativeLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCooperativeLocal.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCooperativeLocal))
            )
            .andExpect(status().isOk());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
        CooperativeLocal testCooperativeLocal = cooperativeLocalList.get(cooperativeLocalList.size() - 1);
        assertThat(testCooperativeLocal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCooperativeLocal.getZone()).isEqualTo(UPDATED_ZONE);
    }

    @Test
    @Transactional
    void patchNonExistingCooperativeLocal() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();
        cooperativeLocal.setId(count.incrementAndGet());

        // Create the CooperativeLocal
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCooperativeLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cooperativeLocalDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCooperativeLocal() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();
        cooperativeLocal.setId(count.incrementAndGet());

        // Create the CooperativeLocal
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeLocalMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCooperativeLocal() throws Exception {
        int databaseSizeBeforeUpdate = cooperativeLocalRepository.findAll().size();
        cooperativeLocal.setId(count.incrementAndGet());

        // Create the CooperativeLocal
        CooperativeLocalDTO cooperativeLocalDTO = cooperativeLocalMapper.toDto(cooperativeLocal);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCooperativeLocalMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cooperativeLocalDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CooperativeLocal in the database
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCooperativeLocal() throws Exception {
        // Initialize the database
        cooperativeLocalRepository.saveAndFlush(cooperativeLocal);

        int databaseSizeBeforeDelete = cooperativeLocalRepository.findAll().size();

        // Delete the cooperativeLocal
        restCooperativeLocalMockMvc
            .perform(delete(ENTITY_API_URL_ID, cooperativeLocal.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CooperativeLocal> cooperativeLocalList = cooperativeLocalRepository.findAll();
        assertThat(cooperativeLocalList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
