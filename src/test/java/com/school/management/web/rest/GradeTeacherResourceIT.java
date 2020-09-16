package com.school.management.web.rest;

import com.school.management.SchoolApp;
import com.school.management.domain.GradeTeacher;
import com.school.management.repository.GradeTeacherRepository;
import com.school.management.service.GradeTeacherService;
import com.school.management.service.dto.GradeTeacherDTO;
import com.school.management.service.mapper.GradeTeacherMapper;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GradeTeacherResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GradeTeacherResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private GradeTeacherRepository gradeTeacherRepository;

    @Autowired
    private GradeTeacherMapper gradeTeacherMapper;

    @Autowired
    private GradeTeacherService gradeTeacherService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGradeTeacherMockMvc;

    private GradeTeacher gradeTeacher;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeTeacher createEntity(EntityManager em) {
        GradeTeacher gradeTeacher = new GradeTeacher()
            .date(DEFAULT_DATE);
        return gradeTeacher;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GradeTeacher createUpdatedEntity(EntityManager em) {
        GradeTeacher gradeTeacher = new GradeTeacher()
            .date(UPDATED_DATE);
        return gradeTeacher;
    }

    @BeforeEach
    public void initTest() {
        gradeTeacher = createEntity(em);
    }

    @Test
    @Transactional
    public void createGradeTeacher() throws Exception {
        int databaseSizeBeforeCreate = gradeTeacherRepository.findAll().size();
        // Create the GradeTeacher
        GradeTeacherDTO gradeTeacherDTO = gradeTeacherMapper.toDto(gradeTeacher);
        restGradeTeacherMockMvc.perform(post("/api/grade-teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeTeacherDTO)))
            .andExpect(status().isCreated());

        // Validate the GradeTeacher in the database
        List<GradeTeacher> gradeTeacherList = gradeTeacherRepository.findAll();
        assertThat(gradeTeacherList).hasSize(databaseSizeBeforeCreate + 1);
        GradeTeacher testGradeTeacher = gradeTeacherList.get(gradeTeacherList.size() - 1);
        assertThat(testGradeTeacher.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createGradeTeacherWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = gradeTeacherRepository.findAll().size();

        // Create the GradeTeacher with an existing ID
        gradeTeacher.setId(1L);
        GradeTeacherDTO gradeTeacherDTO = gradeTeacherMapper.toDto(gradeTeacher);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGradeTeacherMockMvc.perform(post("/api/grade-teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeTeacherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GradeTeacher in the database
        List<GradeTeacher> gradeTeacherList = gradeTeacherRepository.findAll();
        assertThat(gradeTeacherList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGradeTeachers() throws Exception {
        // Initialize the database
        gradeTeacherRepository.saveAndFlush(gradeTeacher);

        // Get all the gradeTeacherList
        restGradeTeacherMockMvc.perform(get("/api/grade-teachers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gradeTeacher.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getGradeTeacher() throws Exception {
        // Initialize the database
        gradeTeacherRepository.saveAndFlush(gradeTeacher);

        // Get the gradeTeacher
        restGradeTeacherMockMvc.perform(get("/api/grade-teachers/{id}", gradeTeacher.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gradeTeacher.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingGradeTeacher() throws Exception {
        // Get the gradeTeacher
        restGradeTeacherMockMvc.perform(get("/api/grade-teachers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGradeTeacher() throws Exception {
        // Initialize the database
        gradeTeacherRepository.saveAndFlush(gradeTeacher);

        int databaseSizeBeforeUpdate = gradeTeacherRepository.findAll().size();

        // Update the gradeTeacher
        GradeTeacher updatedGradeTeacher = gradeTeacherRepository.findById(gradeTeacher.getId()).get();
        // Disconnect from session so that the updates on updatedGradeTeacher are not directly saved in db
        em.detach(updatedGradeTeacher);
        updatedGradeTeacher
            .date(UPDATED_DATE);
        GradeTeacherDTO gradeTeacherDTO = gradeTeacherMapper.toDto(updatedGradeTeacher);

        restGradeTeacherMockMvc.perform(put("/api/grade-teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeTeacherDTO)))
            .andExpect(status().isOk());

        // Validate the GradeTeacher in the database
        List<GradeTeacher> gradeTeacherList = gradeTeacherRepository.findAll();
        assertThat(gradeTeacherList).hasSize(databaseSizeBeforeUpdate);
        GradeTeacher testGradeTeacher = gradeTeacherList.get(gradeTeacherList.size() - 1);
        assertThat(testGradeTeacher.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingGradeTeacher() throws Exception {
        int databaseSizeBeforeUpdate = gradeTeacherRepository.findAll().size();

        // Create the GradeTeacher
        GradeTeacherDTO gradeTeacherDTO = gradeTeacherMapper.toDto(gradeTeacher);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGradeTeacherMockMvc.perform(put("/api/grade-teachers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeTeacherDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GradeTeacher in the database
        List<GradeTeacher> gradeTeacherList = gradeTeacherRepository.findAll();
        assertThat(gradeTeacherList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGradeTeacher() throws Exception {
        // Initialize the database
        gradeTeacherRepository.saveAndFlush(gradeTeacher);

        int databaseSizeBeforeDelete = gradeTeacherRepository.findAll().size();

        // Delete the gradeTeacher
        restGradeTeacherMockMvc.perform(delete("/api/grade-teachers/{id}", gradeTeacher.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GradeTeacher> gradeTeacherList = gradeTeacherRepository.findAll();
        assertThat(gradeTeacherList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
