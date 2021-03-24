package org.jhipster.fitcallab.web.rest;

import org.jhipster.fitcallab.FitcallabApp;
import org.jhipster.fitcallab.domain.Workout;
import org.jhipster.fitcallab.repository.WorkoutRepository;

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

/**
 * Integration tests for the {@link WorkoutResource} REST controller.
 */
@SpringBootTest(classes = FitcallabApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkoutResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MINUTES = 1;
    private static final Integer UPDATED_MINUTES = 2;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkoutMockMvc;

    private Workout workout;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workout createEntity(EntityManager em) {
        Workout workout = new Workout()
            .type(DEFAULT_TYPE)
            .minutes(DEFAULT_MINUTES);
        return workout;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workout createUpdatedEntity(EntityManager em) {
        Workout workout = new Workout()
            .type(UPDATED_TYPE)
            .minutes(UPDATED_MINUTES);
        return workout;
    }

    @BeforeEach
    public void initTest() {
        workout = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkout() throws Exception {
        int databaseSizeBeforeCreate = workoutRepository.findAll().size();
        // Create the Workout
        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workout)))
            .andExpect(status().isCreated());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeCreate + 1);
        Workout testWorkout = workoutList.get(workoutList.size() - 1);
        assertThat(testWorkout.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testWorkout.getMinutes()).isEqualTo(DEFAULT_MINUTES);
    }

    @Test
    @Transactional
    public void createWorkoutWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workoutRepository.findAll().size();

        // Create the Workout with an existing ID
        workout.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workout)))
            .andExpect(status().isBadRequest());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutRepository.findAll().size();
        // set the field null
        workout.setType(null);

        // Create the Workout, which fails.


        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workout)))
            .andExpect(status().isBadRequest());

        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMinutesIsRequired() throws Exception {
        int databaseSizeBeforeTest = workoutRepository.findAll().size();
        // set the field null
        workout.setMinutes(null);

        // Create the Workout, which fails.


        restWorkoutMockMvc.perform(post("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workout)))
            .andExpect(status().isBadRequest());

        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllWorkouts() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        // Get all the workoutList
        restWorkoutMockMvc.perform(get("/api/workouts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workout.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].minutes").value(hasItem(DEFAULT_MINUTES)));
    }
    
    @Test
    @Transactional
    public void getWorkout() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        // Get the workout
        restWorkoutMockMvc.perform(get("/api/workouts/{id}", workout.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workout.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.minutes").value(DEFAULT_MINUTES));
    }
    @Test
    @Transactional
    public void getNonExistingWorkout() throws Exception {
        // Get the workout
        restWorkoutMockMvc.perform(get("/api/workouts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkout() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        int databaseSizeBeforeUpdate = workoutRepository.findAll().size();

        // Update the workout
        Workout updatedWorkout = workoutRepository.findById(workout.getId()).get();
        // Disconnect from session so that the updates on updatedWorkout are not directly saved in db
        em.detach(updatedWorkout);
        updatedWorkout
            .type(UPDATED_TYPE)
            .minutes(UPDATED_MINUTES);

        restWorkoutMockMvc.perform(put("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkout)))
            .andExpect(status().isOk());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeUpdate);
        Workout testWorkout = workoutList.get(workoutList.size() - 1);
        assertThat(testWorkout.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testWorkout.getMinutes()).isEqualTo(UPDATED_MINUTES);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkout() throws Exception {
        int databaseSizeBeforeUpdate = workoutRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkoutMockMvc.perform(put("/api/workouts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workout)))
            .andExpect(status().isBadRequest());

        // Validate the Workout in the database
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkout() throws Exception {
        // Initialize the database
        workoutRepository.saveAndFlush(workout);

        int databaseSizeBeforeDelete = workoutRepository.findAll().size();

        // Delete the workout
        restWorkoutMockMvc.perform(delete("/api/workouts/{id}", workout.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Workout> workoutList = workoutRepository.findAll();
        assertThat(workoutList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
