package com.school.management.web.rest;

import com.school.management.SchoolApp;
import com.school.management.domain.Attendence;
import com.school.management.repository.AttendenceRepository;
import com.school.management.service.AttendenceService;
import com.school.management.service.dto.AttendenceDTO;
import com.school.management.service.mapper.AttendenceMapper;

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
 * Integration tests for the {@link AttendenceResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendenceResourceIT {

    @Autowired
    private AttendenceRepository attendenceRepository;

    @Autowired
    private AttendenceMapper attendenceMapper;

    @Autowired
    private AttendenceService attendenceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendenceMockMvc;

    private Attendence attendence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendence createEntity(EntityManager em) {
        Attendence attendence = new Attendence();
        return attendence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Attendence createUpdatedEntity(EntityManager em) {
        Attendence attendence = new Attendence();
        return attendence;
    }

    @BeforeEach
    public void initTest() {
        attendence = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendence() throws Exception {
        int databaseSizeBeforeCreate = attendenceRepository.findAll().size();
        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);
        restAttendenceMockMvc.perform(post("/api/attendences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Attendence in the database
        List<Attendence> attendenceList = attendenceRepository.findAll();
        assertThat(attendenceList).hasSize(databaseSizeBeforeCreate + 1);
        Attendence testAttendence = attendenceList.get(attendenceList.size() - 1);
    }

    @Test
    @Transactional
    public void createAttendenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendenceRepository.findAll().size();

        // Create the Attendence with an existing ID
        attendence.setId(1L);
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendenceMockMvc.perform(post("/api/attendences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendence in the database
        List<Attendence> attendenceList = attendenceRepository.findAll();
        assertThat(attendenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendences() throws Exception {
        // Initialize the database
        attendenceRepository.saveAndFlush(attendence);

        // Get all the attendenceList
        restAttendenceMockMvc.perform(get("/api/attendences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendence.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAttendence() throws Exception {
        // Initialize the database
        attendenceRepository.saveAndFlush(attendence);

        // Get the attendence
        restAttendenceMockMvc.perform(get("/api/attendences/{id}", attendence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendence.getId().intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingAttendence() throws Exception {
        // Get the attendence
        restAttendenceMockMvc.perform(get("/api/attendences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendence() throws Exception {
        // Initialize the database
        attendenceRepository.saveAndFlush(attendence);

        int databaseSizeBeforeUpdate = attendenceRepository.findAll().size();

        // Update the attendence
        Attendence updatedAttendence = attendenceRepository.findById(attendence.getId()).get();
        // Disconnect from session so that the updates on updatedAttendence are not directly saved in db
        em.detach(updatedAttendence);
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(updatedAttendence);

        restAttendenceMockMvc.perform(put("/api/attendences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDTO)))
            .andExpect(status().isOk());

        // Validate the Attendence in the database
        List<Attendence> attendenceList = attendenceRepository.findAll();
        assertThat(attendenceList).hasSize(databaseSizeBeforeUpdate);
        Attendence testAttendence = attendenceList.get(attendenceList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendence() throws Exception {
        int databaseSizeBeforeUpdate = attendenceRepository.findAll().size();

        // Create the Attendence
        AttendenceDTO attendenceDTO = attendenceMapper.toDto(attendence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendenceMockMvc.perform(put("/api/attendences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Attendence in the database
        List<Attendence> attendenceList = attendenceRepository.findAll();
        assertThat(attendenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendence() throws Exception {
        // Initialize the database
        attendenceRepository.saveAndFlush(attendence);

        int databaseSizeBeforeDelete = attendenceRepository.findAll().size();

        // Delete the attendence
        restAttendenceMockMvc.perform(delete("/api/attendences/{id}", attendence.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Attendence> attendenceList = attendenceRepository.findAll();
        assertThat(attendenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
