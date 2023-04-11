package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Restorateur;
import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.repository.RestorateurRepository;
import com.mycompany.myapp.service.dto.RestorateurDTO;
import com.mycompany.myapp.service.mapper.RestorateurMapper;
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
 * Integration tests for the {@link RestorateurResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RestorateurResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_THEME = "AAAAAAAAAA";
    private static final String UPDATED_THEME = "BBBBBBBBBB";

    private static final String DEFAULT_ZONE = "AAAAAAAAAA";
    private static final String UPDATED_ZONE = "BBBBBBBBBB";

    private static final String DEFAULT_OPTIONS = "AAAAAAAAAA";
    private static final String UPDATED_OPTIONS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/restorateurs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RestorateurRepository restorateurRepository;

    @Autowired
    private RestorateurMapper restorateurMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRestorateurMockMvc;

    private Restorateur restorateur;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restorateur createEntity(EntityManager em) {
        Restorateur restorateur = new Restorateur().nom(DEFAULT_NOM).theme(DEFAULT_THEME).zone(DEFAULT_ZONE).options(DEFAULT_OPTIONS);
        // Add required entity
        Societaire societaire;
        if (TestUtil.findAll(em, Societaire.class).isEmpty()) {
            societaire = SocietaireResourceIT.createEntity(em);
            em.persist(societaire);
            em.flush();
        } else {
            societaire = TestUtil.findAll(em, Societaire.class).get(0);
        }
        restorateur.setListname(societaire);
        return restorateur;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restorateur createUpdatedEntity(EntityManager em) {
        Restorateur restorateur = new Restorateur().nom(UPDATED_NOM).theme(UPDATED_THEME).zone(UPDATED_ZONE).options(UPDATED_OPTIONS);
        // Add required entity
        Societaire societaire;
        if (TestUtil.findAll(em, Societaire.class).isEmpty()) {
            societaire = SocietaireResourceIT.createUpdatedEntity(em);
            em.persist(societaire);
            em.flush();
        } else {
            societaire = TestUtil.findAll(em, Societaire.class).get(0);
        }
        restorateur.setListname(societaire);
        return restorateur;
    }

    @BeforeEach
    public void initTest() {
        restorateur = createEntity(em);
    }

    @Test
    @Transactional
    void createRestorateur() throws Exception {
        int databaseSizeBeforeCreate = restorateurRepository.findAll().size();
        // Create the Restorateur
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);
        restRestorateurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeCreate + 1);
        Restorateur testRestorateur = restorateurList.get(restorateurList.size() - 1);
        assertThat(testRestorateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRestorateur.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testRestorateur.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testRestorateur.getOptions()).isEqualTo(DEFAULT_OPTIONS);
    }

    @Test
    @Transactional
    void createRestorateurWithExistingId() throws Exception {
        // Create the Restorateur with an existing ID
        restorateur.setId(1L);
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        int databaseSizeBeforeCreate = restorateurRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestorateurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = restorateurRepository.findAll().size();
        // set the field null
        restorateur.setNom(null);

        // Create the Restorateur, which fails.
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        restRestorateurMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isBadRequest());

        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRestorateurs() throws Exception {
        // Initialize the database
        restorateurRepository.saveAndFlush(restorateur);

        // Get all the restorateurList
        restRestorateurMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restorateur.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].theme").value(hasItem(DEFAULT_THEME)))
            .andExpect(jsonPath("$.[*].zone").value(hasItem(DEFAULT_ZONE)))
            .andExpect(jsonPath("$.[*].options").value(hasItem(DEFAULT_OPTIONS)));
    }

    @Test
    @Transactional
    void getRestorateur() throws Exception {
        // Initialize the database
        restorateurRepository.saveAndFlush(restorateur);

        // Get the restorateur
        restRestorateurMockMvc
            .perform(get(ENTITY_API_URL_ID, restorateur.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(restorateur.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.theme").value(DEFAULT_THEME))
            .andExpect(jsonPath("$.zone").value(DEFAULT_ZONE))
            .andExpect(jsonPath("$.options").value(DEFAULT_OPTIONS));
    }

    @Test
    @Transactional
    void getNonExistingRestorateur() throws Exception {
        // Get the restorateur
        restRestorateurMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRestorateur() throws Exception {
        // Initialize the database
        restorateurRepository.saveAndFlush(restorateur);

        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();

        // Update the restorateur
        Restorateur updatedRestorateur = restorateurRepository.findById(restorateur.getId()).get();
        // Disconnect from session so that the updates on updatedRestorateur are not directly saved in db
        em.detach(updatedRestorateur);
        updatedRestorateur.nom(UPDATED_NOM).theme(UPDATED_THEME).zone(UPDATED_ZONE).options(UPDATED_OPTIONS);
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(updatedRestorateur);

        restRestorateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restorateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isOk());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
        Restorateur testRestorateur = restorateurList.get(restorateurList.size() - 1);
        assertThat(testRestorateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRestorateur.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testRestorateur.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testRestorateur.getOptions()).isEqualTo(UPDATED_OPTIONS);
    }

    @Test
    @Transactional
    void putNonExistingRestorateur() throws Exception {
        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();
        restorateur.setId(count.incrementAndGet());

        // Create the Restorateur
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestorateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, restorateurDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRestorateur() throws Exception {
        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();
        restorateur.setId(count.incrementAndGet());

        // Create the Restorateur
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorateurMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRestorateur() throws Exception {
        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();
        restorateur.setId(count.incrementAndGet());

        // Create the Restorateur
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorateurMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(restorateurDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRestorateurWithPatch() throws Exception {
        // Initialize the database
        restorateurRepository.saveAndFlush(restorateur);

        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();

        // Update the restorateur using partial update
        Restorateur partialUpdatedRestorateur = new Restorateur();
        partialUpdatedRestorateur.setId(restorateur.getId());

        restRestorateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestorateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestorateur))
            )
            .andExpect(status().isOk());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
        Restorateur testRestorateur = restorateurList.get(restorateurList.size() - 1);
        assertThat(testRestorateur.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRestorateur.getTheme()).isEqualTo(DEFAULT_THEME);
        assertThat(testRestorateur.getZone()).isEqualTo(DEFAULT_ZONE);
        assertThat(testRestorateur.getOptions()).isEqualTo(DEFAULT_OPTIONS);
    }

    @Test
    @Transactional
    void fullUpdateRestorateurWithPatch() throws Exception {
        // Initialize the database
        restorateurRepository.saveAndFlush(restorateur);

        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();

        // Update the restorateur using partial update
        Restorateur partialUpdatedRestorateur = new Restorateur();
        partialUpdatedRestorateur.setId(restorateur.getId());

        partialUpdatedRestorateur.nom(UPDATED_NOM).theme(UPDATED_THEME).zone(UPDATED_ZONE).options(UPDATED_OPTIONS);

        restRestorateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRestorateur.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRestorateur))
            )
            .andExpect(status().isOk());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
        Restorateur testRestorateur = restorateurList.get(restorateurList.size() - 1);
        assertThat(testRestorateur.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRestorateur.getTheme()).isEqualTo(UPDATED_THEME);
        assertThat(testRestorateur.getZone()).isEqualTo(UPDATED_ZONE);
        assertThat(testRestorateur.getOptions()).isEqualTo(UPDATED_OPTIONS);
    }

    @Test
    @Transactional
    void patchNonExistingRestorateur() throws Exception {
        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();
        restorateur.setId(count.incrementAndGet());

        // Create the Restorateur
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestorateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, restorateurDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRestorateur() throws Exception {
        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();
        restorateur.setId(count.incrementAndGet());

        // Create the Restorateur
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorateurMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRestorateur() throws Exception {
        int databaseSizeBeforeUpdate = restorateurRepository.findAll().size();
        restorateur.setId(count.incrementAndGet());

        // Create the Restorateur
        RestorateurDTO restorateurDTO = restorateurMapper.toDto(restorateur);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRestorateurMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(restorateurDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Restorateur in the database
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRestorateur() throws Exception {
        // Initialize the database
        restorateurRepository.saveAndFlush(restorateur);

        int databaseSizeBeforeDelete = restorateurRepository.findAll().size();

        // Delete the restorateur
        restRestorateurMockMvc
            .perform(delete(ENTITY_API_URL_ID, restorateur.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Restorateur> restorateurList = restorateurRepository.findAll();
        assertThat(restorateurList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
