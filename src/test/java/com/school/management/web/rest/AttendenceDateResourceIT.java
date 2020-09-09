package com.school.management.web.rest;

import com.school.management.SchoolApp;
import com.school.management.domain.AttendenceDate;
import com.school.management.repository.AttendenceDateRepository;
import com.school.management.service.AttendenceDateService;
import com.school.management.service.dto.AttendenceDateDTO;
import com.school.management.service.mapper.AttendenceDateMapper;

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
 * Integration tests for the {@link AttendenceDateResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendenceDateResourceIT {

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AttendenceDateRepository attendenceDateRepository;

    @Autowired
    private AttendenceDateMapper attendenceDateMapper;

    @Autowired
    private AttendenceDateService attendenceDateService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendenceDateMockMvc;

    private AttendenceDate attendenceDate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendenceDate createEntity(EntityManager em) {
        AttendenceDate attendenceDate = new AttendenceDate()
            .date(DEFAULT_DATE);
        return attendenceDate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AttendenceDate createUpdatedEntity(EntityManager em) {
        AttendenceDate attendenceDate = new AttendenceDate()
            .date(UPDATED_DATE);
        return attendenceDate;
    }

    @BeforeEach
    public void initTest() {
        attendenceDate = createEntity(em);
    }

    @Test
    @Transactional
    public void createAttendenceDate() throws Exception {
        int databaseSizeBeforeCreate = attendenceDateRepository.findAll().size();
        // Create the AttendenceDate
        AttendenceDateDTO attendenceDateDTO = attendenceDateMapper.toDto(attendenceDate);
        restAttendenceDateMockMvc.perform(post("/api/attendence-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDateDTO)))
            .andExpect(status().isCreated());

        // Validate the AttendenceDate in the database
        List<AttendenceDate> attendenceDateList = attendenceDateRepository.findAll();
        assertThat(attendenceDateList).hasSize(databaseSizeBeforeCreate + 1);
        AttendenceDate testAttendenceDate = attendenceDateList.get(attendenceDateList.size() - 1);
        assertThat(testAttendenceDate.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createAttendenceDateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = attendenceDateRepository.findAll().size();

        // Create the AttendenceDate with an existing ID
        attendenceDate.setId(1L);
        AttendenceDateDTO attendenceDateDTO = attendenceDateMapper.toDto(attendenceDate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendenceDateMockMvc.perform(post("/api/attendence-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendenceDate in the database
        List<AttendenceDate> attendenceDateList = attendenceDateRepository.findAll();
        assertThat(attendenceDateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendenceDates() throws Exception {
        // Initialize the database
        attendenceDateRepository.saveAndFlush(attendenceDate);

        // Get all the attendenceDateList
        restAttendenceDateMockMvc.perform(get("/api/attendence-dates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendenceDate.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getAttendenceDate() throws Exception {
        // Initialize the database
        attendenceDateRepository.saveAndFlush(attendenceDate);

        // Get the attendenceDate
        restAttendenceDateMockMvc.perform(get("/api/attendence-dates/{id}", attendenceDate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendenceDate.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingAttendenceDate() throws Exception {
        // Get the attendenceDate
        restAttendenceDateMockMvc.perform(get("/api/attendence-dates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendenceDate() throws Exception {
        // Initialize the database
        attendenceDateRepository.saveAndFlush(attendenceDate);

        int databaseSizeBeforeUpdate = attendenceDateRepository.findAll().size();

        // Update the attendenceDate
        AttendenceDate updatedAttendenceDate = attendenceDateRepository.findById(attendenceDate.getId()).get();
        // Disconnect from session so that the updates on updatedAttendenceDate are not directly saved in db
        em.detach(updatedAttendenceDate);
        updatedAttendenceDate
            .date(UPDATED_DATE);
        AttendenceDateDTO attendenceDateDTO = attendenceDateMapper.toDto(updatedAttendenceDate);

        restAttendenceDateMockMvc.perform(put("/api/attendence-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDateDTO)))
            .andExpect(status().isOk());

        // Validate the AttendenceDate in the database
        List<AttendenceDate> attendenceDateList = attendenceDateRepository.findAll();
        assertThat(attendenceDateList).hasSize(databaseSizeBeforeUpdate);
        AttendenceDate testAttendenceDate = attendenceDateList.get(attendenceDateList.size() - 1);
        assertThat(testAttendenceDate.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendenceDate() throws Exception {
        int databaseSizeBeforeUpdate = attendenceDateRepository.findAll().size();

        // Create the AttendenceDate
        AttendenceDateDTO attendenceDateDTO = attendenceDateMapper.toDto(attendenceDate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendenceDateMockMvc.perform(put("/api/attendence-dates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(attendenceDateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendenceDate in the database
        List<AttendenceDate> attendenceDateList = attendenceDateRepository.findAll();
        assertThat(attendenceDateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendenceDate() throws Exception {
        // Initialize the database
        attendenceDateRepository.saveAndFlush(attendenceDate);

        int databaseSizeBeforeDelete = attendenceDateRepository.findAll().size();

        // Delete the attendenceDate
        restAttendenceDateMockMvc.perform(delete("/api/attendence-dates/{id}", attendenceDate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendenceDate> attendenceDateList = attendenceDateRepository.findAll();
        assertThat(attendenceDateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
