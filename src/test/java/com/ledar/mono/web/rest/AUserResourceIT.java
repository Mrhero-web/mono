package com.ledar.mono.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ledar.mono.IntegrationTest;
import com.ledar.mono.domain.AUser;
import com.ledar.mono.repository.AUserRepository;
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
 * Integration tests for the {@link AUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AUserResourceIT {

    private static final String DEFAULT_LOGIN = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/a-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AUserRepository aUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAUserMockMvc;

    private AUser aUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AUser createEntity(EntityManager em) {
        AUser aUser = new AUser().login(DEFAULT_LOGIN).password(DEFAULT_PASSWORD);
        return aUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AUser createUpdatedEntity(EntityManager em) {
        AUser aUser = new AUser().login(UPDATED_LOGIN).password(UPDATED_PASSWORD);
        return aUser;
    }

    @BeforeEach
    public void initTest() {
        aUser = createEntity(em);
    }

    @Test
    @Transactional
    void createAUser() throws Exception {
        int databaseSizeBeforeCreate = aUserRepository.findAll().size();
        // Create the AUser
        restAUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aUser)))
            .andExpect(status().isCreated());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeCreate + 1);
        AUser testAUser = aUserList.get(aUserList.size() - 1);
        assertThat(testAUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testAUser.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    void createAUserWithExistingId() throws Exception {
        // Create the AUser with an existing ID
        aUser.setId(1L);

        int databaseSizeBeforeCreate = aUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aUser)))
            .andExpect(status().isBadRequest());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLoginIsRequired() throws Exception {
        int databaseSizeBeforeTest = aUserRepository.findAll().size();
        // set the field null
        aUser.setLogin(null);

        // Create the AUser, which fails.

        restAUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aUser)))
            .andExpect(status().isBadRequest());

        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = aUserRepository.findAll().size();
        // set the field null
        aUser.setPassword(null);

        // Create the AUser, which fails.

        restAUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aUser)))
            .andExpect(status().isBadRequest());

        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAUsers() throws Exception {
        // Initialize the database
        aUserRepository.saveAndFlush(aUser);

        // Get all the aUserList
        restAUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].login").value(hasItem(DEFAULT_LOGIN)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }

    @Test
    @Transactional
    void getAUser() throws Exception {
        // Initialize the database
        aUserRepository.saveAndFlush(aUser);

        // Get the aUser
        restAUserMockMvc
            .perform(get(ENTITY_API_URL_ID, aUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(aUser.getId().intValue()))
            .andExpect(jsonPath("$.login").value(DEFAULT_LOGIN))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }

    @Test
    @Transactional
    void getNonExistingAUser() throws Exception {
        // Get the aUser
        restAUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAUser() throws Exception {
        // Initialize the database
        aUserRepository.saveAndFlush(aUser);

        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();

        // Update the aUser
        AUser updatedAUser = aUserRepository.findById(aUser.getId()).get();
        // Disconnect from session so that the updates on updatedAUser are not directly saved in db
        em.detach(updatedAUser);
        updatedAUser.login(UPDATED_LOGIN).password(UPDATED_PASSWORD);

        restAUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedAUser))
            )
            .andExpect(status().isOk());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
        AUser testAUser = aUserList.get(aUserList.size() - 1);
        assertThat(testAUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testAUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void putNonExistingAUser() throws Exception {
        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();
        aUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, aUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAUser() throws Exception {
        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();
        aUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(aUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAUser() throws Exception {
        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();
        aUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(aUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAUserWithPatch() throws Exception {
        // Initialize the database
        aUserRepository.saveAndFlush(aUser);

        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();

        // Update the aUser using partial update
        AUser partialUpdatedAUser = new AUser();
        partialUpdatedAUser.setId(aUser.getId());

        partialUpdatedAUser.password(UPDATED_PASSWORD);

        restAUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAUser))
            )
            .andExpect(status().isOk());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
        AUser testAUser = aUserList.get(aUserList.size() - 1);
        assertThat(testAUser.getLogin()).isEqualTo(DEFAULT_LOGIN);
        assertThat(testAUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void fullUpdateAUserWithPatch() throws Exception {
        // Initialize the database
        aUserRepository.saveAndFlush(aUser);

        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();

        // Update the aUser using partial update
        AUser partialUpdatedAUser = new AUser();
        partialUpdatedAUser.setId(aUser.getId());

        partialUpdatedAUser.login(UPDATED_LOGIN).password(UPDATED_PASSWORD);

        restAUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAUser))
            )
            .andExpect(status().isOk());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
        AUser testAUser = aUserList.get(aUserList.size() - 1);
        assertThat(testAUser.getLogin()).isEqualTo(UPDATED_LOGIN);
        assertThat(testAUser.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    void patchNonExistingAUser() throws Exception {
        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();
        aUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, aUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAUser() throws Exception {
        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();
        aUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(aUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAUser() throws Exception {
        int databaseSizeBeforeUpdate = aUserRepository.findAll().size();
        aUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAUserMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(aUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AUser in the database
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAUser() throws Exception {
        // Initialize the database
        aUserRepository.saveAndFlush(aUser);

        int databaseSizeBeforeDelete = aUserRepository.findAll().size();

        // Delete the aUser
        restAUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, aUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AUser> aUserList = aUserRepository.findAll();
        assertThat(aUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
