package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Conseil;
import com.mycompany.myapp.domain.CooperativeLocal;
import com.mycompany.myapp.domain.Societaire;
import com.mycompany.myapp.repository.SocietaireRepository;
import com.mycompany.myapp.service.dto.SocietaireDTO;
import com.mycompany.myapp.service.mapper.SocietaireMapper;
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
 * Integration tests for the {@link SocietaireResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocietaireResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTEUR = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTEUR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/societaires";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocietaireRepository societaireRepository;

    @Autowired
    private SocietaireMapper societaireMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocietaireMockMvc;

    private Societaire societaire;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societaire createEntity(EntityManager em) {
        Societaire societaire = new Societaire().nom(DEFAULT_NOM).directeur(DEFAULT_DIRECTEUR);
        // Add required entity
        CooperativeLocal cooperativeLocal;
        if (TestUtil.findAll(em, CooperativeLocal.class).isEmpty()) {
            cooperativeLocal = CooperativeLocalResourceIT.createEntity(em);
            em.persist(cooperativeLocal);
            em.flush();
        } else {
            cooperativeLocal = TestUtil.findAll(em, CooperativeLocal.class).get(0);
        }
        societaire.setCoopname(cooperativeLocal);
        // Add required entity
        Conseil conseil;
        if (TestUtil.findAll(em, Conseil.class).isEmpty()) {
            conseil = ConseilResourceIT.createEntity(em);
            em.persist(conseil);
            em.flush();
        } else {
            conseil = TestUtil.findAll(em, Conseil.class).get(0);
        }
        societaire.setConsname(conseil);
        return societaire;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Societaire createUpdatedEntity(EntityManager em) {
        Societaire societaire = new Societaire().nom(UPDATED_NOM).directeur(UPDATED_DIRECTEUR);
        // Add required entity
        CooperativeLocal cooperativeLocal;
        if (TestUtil.findAll(em, CooperativeLocal.class).isEmpty()) {
            cooperativeLocal = CooperativeLocalResourceIT.createUpdatedEntity(em);
            em.persist(cooperativeLocal);
            em.flush();
        } else {
            cooperativeLocal = TestUtil.findAll(em, CooperativeLocal.class).get(0);
        }
        societaire.setCoopname(cooperativeLocal);
        // Add required entity
        Conseil conseil;
        if (TestUtil.findAll(em, Conseil.class).isEmpty()) {
            conseil = ConseilResourceIT.createUpdatedEntity(em);
            em.persist(conseil);
            em.flush();
        } else {
            conseil = TestUtil.findAll(em, Conseil.class).get(0);
        }
        societaire.setConsname(conseil);
        return societaire;
    }

    @BeforeEach
    public void initTest() {
        societaire = createEntity(em);
    }

    @Test
    @Transactional
    void createSocietaire() throws Exception {
        int databaseSizeBeforeCreate = societaireRepository.findAll().size();
        // Create the Societaire
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);
        restSocietaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societaireDTO)))
            .andExpect(status().isCreated());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeCreate + 1);
        Societaire testSocietaire = societaireList.get(societaireList.size() - 1);
        assertThat(testSocietaire.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testSocietaire.getDirecteur()).isEqualTo(DEFAULT_DIRECTEUR);
    }

    @Test
    @Transactional
    void createSocietaireWithExistingId() throws Exception {
        // Create the Societaire with an existing ID
        societaire.setId(1L);
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        int databaseSizeBeforeCreate = societaireRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocietaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societaireDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = societaireRepository.findAll().size();
        // set the field null
        societaire.setNom(null);

        // Create the Societaire, which fails.
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        restSocietaireMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societaireDTO)))
            .andExpect(status().isBadRequest());

        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSocietaires() throws Exception {
        // Initialize the database
        societaireRepository.saveAndFlush(societaire);

        // Get all the societaireList
        restSocietaireMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].directeur").value(hasItem(DEFAULT_DIRECTEUR)));
    }

    @Test
    @Transactional
    void getSocietaire() throws Exception {
        // Initialize the database
        societaireRepository.saveAndFlush(societaire);

        // Get the societaire
        restSocietaireMockMvc
            .perform(get(ENTITY_API_URL_ID, societaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(societaire.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.directeur").value(DEFAULT_DIRECTEUR));
    }

    @Test
    @Transactional
    void getNonExistingSocietaire() throws Exception {
        // Get the societaire
        restSocietaireMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSocietaire() throws Exception {
        // Initialize the database
        societaireRepository.saveAndFlush(societaire);

        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();

        // Update the societaire
        Societaire updatedSocietaire = societaireRepository.findById(societaire.getId()).get();
        // Disconnect from session so that the updates on updatedSocietaire are not directly saved in db
        em.detach(updatedSocietaire);
        updatedSocietaire.nom(UPDATED_NOM).directeur(UPDATED_DIRECTEUR);
        SocietaireDTO societaireDTO = societaireMapper.toDto(updatedSocietaire);

        restSocietaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societaireDTO))
            )
            .andExpect(status().isOk());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
        Societaire testSocietaire = societaireList.get(societaireList.size() - 1);
        assertThat(testSocietaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSocietaire.getDirecteur()).isEqualTo(UPDATED_DIRECTEUR);
    }

    @Test
    @Transactional
    void putNonExistingSocietaire() throws Exception {
        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();
        societaire.setId(count.incrementAndGet());

        // Create the Societaire
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, societaireDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocietaire() throws Exception {
        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();
        societaire.setId(count.incrementAndGet());

        // Create the Societaire
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietaireMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(societaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocietaire() throws Exception {
        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();
        societaire.setId(count.incrementAndGet());

        // Create the Societaire
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietaireMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(societaireDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocietaireWithPatch() throws Exception {
        // Initialize the database
        societaireRepository.saveAndFlush(societaire);

        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();

        // Update the societaire using partial update
        Societaire partialUpdatedSocietaire = new Societaire();
        partialUpdatedSocietaire.setId(societaire.getId());

        partialUpdatedSocietaire.nom(UPDATED_NOM).directeur(UPDATED_DIRECTEUR);

        restSocietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietaire))
            )
            .andExpect(status().isOk());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
        Societaire testSocietaire = societaireList.get(societaireList.size() - 1);
        assertThat(testSocietaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSocietaire.getDirecteur()).isEqualTo(UPDATED_DIRECTEUR);
    }

    @Test
    @Transactional
    void fullUpdateSocietaireWithPatch() throws Exception {
        // Initialize the database
        societaireRepository.saveAndFlush(societaire);

        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();

        // Update the societaire using partial update
        Societaire partialUpdatedSocietaire = new Societaire();
        partialUpdatedSocietaire.setId(societaire.getId());

        partialUpdatedSocietaire.nom(UPDATED_NOM).directeur(UPDATED_DIRECTEUR);

        restSocietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocietaire.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocietaire))
            )
            .andExpect(status().isOk());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
        Societaire testSocietaire = societaireList.get(societaireList.size() - 1);
        assertThat(testSocietaire.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testSocietaire.getDirecteur()).isEqualTo(UPDATED_DIRECTEUR);
    }

    @Test
    @Transactional
    void patchNonExistingSocietaire() throws Exception {
        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();
        societaire.setId(count.incrementAndGet());

        // Create the Societaire
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, societaireDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocietaire() throws Exception {
        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();
        societaire.setId(count.incrementAndGet());

        // Create the Societaire
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietaireMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(societaireDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocietaire() throws Exception {
        int databaseSizeBeforeUpdate = societaireRepository.findAll().size();
        societaire.setId(count.incrementAndGet());

        // Create the Societaire
        SocietaireDTO societaireDTO = societaireMapper.toDto(societaire);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocietaireMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(societaireDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Societaire in the database
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocietaire() throws Exception {
        // Initialize the database
        societaireRepository.saveAndFlush(societaire);

        int databaseSizeBeforeDelete = societaireRepository.findAll().size();

        // Delete the societaire
        restSocietaireMockMvc
            .perform(delete(ENTITY_API_URL_ID, societaire.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Societaire> societaireList = societaireRepository.findAll();
        assertThat(societaireList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
