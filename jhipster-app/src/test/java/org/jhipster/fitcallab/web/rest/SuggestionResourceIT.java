package org.jhipster.fitcallab.web.rest;

import org.jhipster.fitcallab.FitcallabApp;
import org.jhipster.fitcallab.domain.Suggestion;
import org.jhipster.fitcallab.repository.SuggestionRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.jhipster.fitcallab.domain.enumeration.DayOfWeek;
import org.jhipster.fitcallab.domain.enumeration.Month;
/**
 * Integration tests for the {@link SuggestionResource} REST controller.
 */
@SpringBootTest(classes = FitcallabApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SuggestionResourceIT {

    private static final DayOfWeek DEFAULT_DAY_OF_WEEK = DayOfWeek.Mon;
    private static final DayOfWeek UPDATED_DAY_OF_WEEK = DayOfWeek.Tue;

    private static final Month DEFAULT_MONTH = Month.Jan;
    private static final Month UPDATED_MONTH = Month.Feb;

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    private static final String DEFAULT_START_TIME = "AAAAAAAA";
    private static final String UPDATED_START_TIME = "BBBBBBBB";

    private static final String DEFAULT_SUGGESTION = "AAAAAAAAAA";
    private static final String UPDATED_SUGGESTION = "BBBBBBBBBB";

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSuggestionMockMvc;

    private Suggestion suggestion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suggestion createEntity(EntityManager em) {
        Suggestion suggestion = new Suggestion()
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .month(DEFAULT_MONTH)
            .day(DEFAULT_DAY)
            .startTime(DEFAULT_START_TIME)
            .suggestion(DEFAULT_SUGGESTION);
        return suggestion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Suggestion createUpdatedEntity(EntityManager em) {
        Suggestion suggestion = new Suggestion()
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .month(UPDATED_MONTH)
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .suggestion(UPDATED_SUGGESTION);
        return suggestion;
    }

    @BeforeEach
    public void initTest() {
        suggestion = createEntity(em);
    }

    @Test
    @Transactional
    public void createSuggestion() throws Exception {
        int databaseSizeBeforeCreate = suggestionRepository.findAll().size();
        // Create the Suggestion
        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isCreated());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeCreate + 1);
        Suggestion testSuggestion = suggestionList.get(suggestionList.size() - 1);
        assertThat(testSuggestion.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testSuggestion.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testSuggestion.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testSuggestion.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testSuggestion.getSuggestion()).isEqualTo(DEFAULT_SUGGESTION);
    }

    @Test
    @Transactional
    public void createSuggestionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = suggestionRepository.findAll().size();

        // Create the Suggestion with an existing ID
        suggestion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDayOfWeekIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setDayOfWeek(null);

        // Create the Suggestion, which fails.


        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setMonth(null);

        // Create the Suggestion, which fails.


        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setDay(null);

        // Create the Suggestion, which fails.


        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setStartTime(null);

        // Create the Suggestion, which fails.


        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSuggestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = suggestionRepository.findAll().size();
        // set the field null
        suggestion.setSuggestion(null);

        // Create the Suggestion, which fails.


        restSuggestionMockMvc.perform(post("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSuggestions() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        // Get all the suggestionList
        restSuggestionMockMvc.perform(get("/api/suggestions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(suggestion.getId().intValue())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH.toString())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME)))
            .andExpect(jsonPath("$.[*].suggestion").value(hasItem(DEFAULT_SUGGESTION)));
    }
    
    @Test
    @Transactional
    public void getSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        // Get the suggestion
        restSuggestionMockMvc.perform(get("/api/suggestions/{id}", suggestion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(suggestion.getId().intValue()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK.toString()))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH.toString()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME))
            .andExpect(jsonPath("$.suggestion").value(DEFAULT_SUGGESTION));
    }
    @Test
    @Transactional
    public void getNonExistingSuggestion() throws Exception {
        // Get the suggestion
        restSuggestionMockMvc.perform(get("/api/suggestions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        int databaseSizeBeforeUpdate = suggestionRepository.findAll().size();

        // Update the suggestion
        Suggestion updatedSuggestion = suggestionRepository.findById(suggestion.getId()).get();
        // Disconnect from session so that the updates on updatedSuggestion are not directly saved in db
        em.detach(updatedSuggestion);
        updatedSuggestion
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .month(UPDATED_MONTH)
            .day(UPDATED_DAY)
            .startTime(UPDATED_START_TIME)
            .suggestion(UPDATED_SUGGESTION);

        restSuggestionMockMvc.perform(put("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSuggestion)))
            .andExpect(status().isOk());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeUpdate);
        Suggestion testSuggestion = suggestionList.get(suggestionList.size() - 1);
        assertThat(testSuggestion.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testSuggestion.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testSuggestion.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testSuggestion.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testSuggestion.getSuggestion()).isEqualTo(UPDATED_SUGGESTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSuggestion() throws Exception {
        int databaseSizeBeforeUpdate = suggestionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSuggestionMockMvc.perform(put("/api/suggestions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(suggestion)))
            .andExpect(status().isBadRequest());

        // Validate the Suggestion in the database
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSuggestion() throws Exception {
        // Initialize the database
        suggestionRepository.saveAndFlush(suggestion);

        int databaseSizeBeforeDelete = suggestionRepository.findAll().size();

        // Delete the suggestion
        restSuggestionMockMvc.perform(delete("/api/suggestions/{id}", suggestion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Suggestion> suggestionList = suggestionRepository.findAll();
        assertThat(suggestionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
