package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Conseil;
import com.mycompany.myapp.repository.ConseilRepository;
import com.mycompany.myapp.service.dto.ConseilDTO;
import com.mycompany.myapp.service.mapper.ConseilMapper;
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
 * Integration tests for the {@link ConseilResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConseilResourceIT {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/conseils";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConseilRepository conseilRepository;

    @Autowired
    private ConseilMapper conseilMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConseilMockMvc;

    private Conseil conseil;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conseil createEntity(EntityManager em) {
        Conseil conseil = new Conseil().nom(DEFAULT_NOM);
        return conseil;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Conseil createUpdatedEntity(EntityManager em) {
        Conseil conseil = new Conseil().nom(UPDATED_NOM);
        return conseil;
    }

    @BeforeEach
    public void initTest() {
        conseil = createEntity(em);
    }

    @Test
    @Transactional
    void createConseil() throws Exception {
        int databaseSizeBeforeCreate = conseilRepository.findAll().size();
        // Create the Conseil
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);
        restConseilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conseilDTO)))
            .andExpect(status().isCreated());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeCreate + 1);
        Conseil testConseil = conseilList.get(conseilList.size() - 1);
        assertThat(testConseil.getNom()).isEqualTo(DEFAULT_NOM);
    }

    @Test
    @Transactional
    void createConseilWithExistingId() throws Exception {
        // Create the Conseil with an existing ID
        conseil.setId(1L);
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        int databaseSizeBeforeCreate = conseilRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConseilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conseilDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = conseilRepository.findAll().size();
        // set the field null
        conseil.setNom(null);

        // Create the Conseil, which fails.
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        restConseilMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conseilDTO)))
            .andExpect(status().isBadRequest());

        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConseils() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        // Get all the conseilList
        restConseilMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conseil.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)));
    }

    @Test
    @Transactional
    void getConseil() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        // Get the conseil
        restConseilMockMvc
            .perform(get(ENTITY_API_URL_ID, conseil.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(conseil.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM));
    }

    @Test
    @Transactional
    void getNonExistingConseil() throws Exception {
        // Get the conseil
        restConseilMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingConseil() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();

        // Update the conseil
        Conseil updatedConseil = conseilRepository.findById(conseil.getId()).get();
        // Disconnect from session so that the updates on updatedConseil are not directly saved in db
        em.detach(updatedConseil);
        updatedConseil.nom(UPDATED_NOM);
        ConseilDTO conseilDTO = conseilMapper.toDto(updatedConseil);

        restConseilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conseilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseilDTO))
            )
            .andExpect(status().isOk());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
        Conseil testConseil = conseilList.get(conseilList.size() - 1);
        assertThat(testConseil.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void putNonExistingConseil() throws Exception {
        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();
        conseil.setId(count.incrementAndGet());

        // Create the Conseil
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConseilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, conseilDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConseil() throws Exception {
        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();
        conseil.setId(count.incrementAndGet());

        // Create the Conseil
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseilMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(conseilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConseil() throws Exception {
        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();
        conseil.setId(count.incrementAndGet());

        // Create the Conseil
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseilMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(conseilDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConseilWithPatch() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();

        // Update the conseil using partial update
        Conseil partialUpdatedConseil = new Conseil();
        partialUpdatedConseil.setId(conseil.getId());

        partialUpdatedConseil.nom(UPDATED_NOM);

        restConseilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConseil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConseil))
            )
            .andExpect(status().isOk());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
        Conseil testConseil = conseilList.get(conseilList.size() - 1);
        assertThat(testConseil.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void fullUpdateConseilWithPatch() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();

        // Update the conseil using partial update
        Conseil partialUpdatedConseil = new Conseil();
        partialUpdatedConseil.setId(conseil.getId());

        partialUpdatedConseil.nom(UPDATED_NOM);

        restConseilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConseil.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConseil))
            )
            .andExpect(status().isOk());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
        Conseil testConseil = conseilList.get(conseilList.size() - 1);
        assertThat(testConseil.getNom()).isEqualTo(UPDATED_NOM);
    }

    @Test
    @Transactional
    void patchNonExistingConseil() throws Exception {
        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();
        conseil.setId(count.incrementAndGet());

        // Create the Conseil
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConseilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, conseilDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conseilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConseil() throws Exception {
        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();
        conseil.setId(count.incrementAndGet());

        // Create the Conseil
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseilMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(conseilDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConseil() throws Exception {
        int databaseSizeBeforeUpdate = conseilRepository.findAll().size();
        conseil.setId(count.incrementAndGet());

        // Create the Conseil
        ConseilDTO conseilDTO = conseilMapper.toDto(conseil);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConseilMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(conseilDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Conseil in the database
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConseil() throws Exception {
        // Initialize the database
        conseilRepository.saveAndFlush(conseil);

        int databaseSizeBeforeDelete = conseilRepository.findAll().size();

        // Delete the conseil
        restConseilMockMvc
            .perform(delete(ENTITY_API_URL_ID, conseil.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Conseil> conseilList = conseilRepository.findAll();
        assertThat(conseilList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
