package com.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.school.management.SchoolApp;
import com.school.management.domain.Attendence;
import com.school.management.domain.AttendenceStudent;
import com.school.management.domain.Authority;
import com.school.management.domain.Grade;
import com.school.management.domain.GradeTeacher;
import com.school.management.domain.User;
import com.school.management.repository.AttendenceRepository;
import com.school.management.repository.AttendenceStudentRepository;
import com.school.management.repository.GradeRepository;
import com.school.management.repository.GradeTeacherRepository;
import com.school.management.repository.UserRepository;
import com.school.management.security.AuthoritiesConstants;
import com.school.management.service.AttendenceStudentService;
import com.school.management.service.dto.AttendenceStudentDTO;
import com.school.management.service.mapper.AttendenceStudentMapper;
import com.school.management.service.mapper.AttendenceStudentMapperImpl;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AttendenceStudentResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class AttendenceStudentResourceIT {
    private Logger log= LoggerFactory.getLogger(this.getClass());

    private static final String DEFAULT_LOGIN = "johndoe";
    private static final String UPDATED_LOGIN = "jhipster";

    private static final Long DEFAULT_ID = 1L;

    private static final String DEFAULT_PASSWORD = "passjohndoe";
    private static final String UPDATED_PASSWORD = "passjhipster";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";
    private static final String UPDATED_EMAIL = "jhipster@localhost";

    private static final String DEFAULT_FIRSTNAME = "john";
    private static final String UPDATED_FIRSTNAME = "jhipsterFirstName";

    private static final String DEFAULT_LASTNAME = "doe";
    private static final String UPDATED_LASTNAME = "jhipsterLastName";

    private static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
    private static final String UPDATED_IMAGEURL = "http://placehold.it/40x40";

    private static final String DEFAULT_LANGKEY = "en";
    private static final String UPDATED_LANGKEY = "fr";

    private static final String DEFAULT_MARKED = "AAAAAAAAAA";
    private static final String UPDATED_MARKED = "BBBBBBBBBB";

    @Autowired
    private AttendenceStudentRepository attendenceStudentRepository;

    @Autowired
    private AttendenceStudentMapper attendenceStudentMapper;

    @Autowired
    private AttendenceStudentService attendenceStudentService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAttendenceStudentMockMvc;

    private AttendenceStudent attendenceStudent;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttendenceRepository attendenceRepository;

    private User user;

    private Attendence attendence;
    private Grade grade;

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private GradeTeacherRepository gradeTeacherRepository;

    /*
     * sample data for testing
     */
    @BeforeEach
    public void setup() {
        cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).clear();
        cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).clear();


    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static AttendenceStudent createEntity(EntityManager em) {
        AttendenceStudent attendenceStudent = new AttendenceStudent().marked(DEFAULT_MARKED);
        return attendenceStudent;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it, if they test an
     * entity which requires the current entity.
     */
    public static AttendenceStudent createUpdatedEntity(EntityManager em) {
        AttendenceStudent attendenceStudent = new AttendenceStudent().marked(UPDATED_MARKED);
        return attendenceStudent;
    }

    @BeforeEach
    @Transactional
    public void initTest() {
        attendenceStudent = createEntity(em);

        //creating grade
        Grade tempGrade = new Grade();
        tempGrade.setGrade(8);
        tempGrade = gradeRepository.saveAndFlush(tempGrade);
        // creating student
        user = new User();
        user.setLogin(UPDATED_LOGIN);
        user.setEmail(UPDATED_EMAIL);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.USER);
        user.setAuthorities(new HashSet<Authority>(Arrays.asList(authority)));
        user = userRepository.saveAndFlush(user);

        //add grade to db
        Grade grade = new Grade();
        grade.setGrade(8);
        grade = gradeRepository.saveAndFlush(grade);

        // add teacher to db
        User tempUser = new User();
        tempUser.setLogin(DEFAULT_LOGIN);
        tempUser.setEmail(DEFAULT_EMAIL);
        tempUser.setPassword(RandomStringUtils.random(60));
        tempUser.setActivated(true);
        Authority authority1 = new Authority();
        Authority authority2 = new Authority();
        authority1.setName(AuthoritiesConstants.TEACHER);
        authority2.setName(AuthoritiesConstants.USER);
        tempUser.setAuthorities(new HashSet<Authority>(Arrays.asList(authority2, authority1)));
        tempUser = userRepository.save(tempUser);
        Attendence tempAttendence = new Attendence();
        tempAttendence.setUser(tempUser);
        tempAttendence.setGrade(tempGrade);
        tempAttendence = attendenceRepository.saveAndFlush(tempAttendence);

        //add gradeTeacher to db
        GradeTeacher gradeTeacher = new GradeTeacher();
        gradeTeacher.setUser(tempUser);
        gradeTeacher.setGrade(grade);
        gradeTeacherRepository.saveAndFlush(gradeTeacher);

        // pre setup of attendenceStudent
        User refUser = new User();
        Attendence attendence = new Attendence();
        refUser.setId(user.getId());
        attendence.setId(tempAttendence.getId());
        attendenceStudent.setUser(user);
        attendenceStudent.setAttendence(attendence);
    }

    @Test
    @Transactional
    public void createAttendenceStudent() throws Exception {
        int databaseSizeBeforeCreate = attendenceStudentRepository.findAll().size();
        // Create the AttendenceStudent
        AttendenceStudentDTO attendenceStudentDTO = attendenceStudentMapper
            .toDto(attendenceStudent);
        restAttendenceStudentMockMvc
            .perform(post("/api/attendence-students").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(attendenceStudentDTO)))
            .andExpect(status().isCreated());

        // Validate the AttendenceStudent in the database
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeCreate + 1);
        AttendenceStudent testAttendenceStudent =
            attendenceStudentList.get(attendenceStudentList.size() - 1);
        assertThat(testAttendenceStudent.getMarked()).isEqualTo(DEFAULT_MARKED);
    }

    @Test
    @Transactional
    public void createAttendenceStudentWithExistingId() throws Exception {
        // add attendence
        attendenceStudent = attendenceStudentRepository.saveAndFlush(attendenceStudent);
        int databaseSizeBeforeCreate = attendenceStudentRepository.findAll().size();

        AttendenceStudentDTO attendenceStudentDTO = attendenceStudentMapper
            .toDto(attendenceStudent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAttendenceStudentMockMvc
            .perform(post("/api/attendence-students").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(attendenceStudentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendenceStudent in the database
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAttendenceStudents() throws Exception {
        // Initialize the database
        attendenceStudentRepository.saveAndFlush(attendenceStudent);

        // Get all the attendenceStudentList
        restAttendenceStudentMockMvc.perform(get("/api/attendence-students?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(attendenceStudent.getId().intValue())))
            .andExpect(jsonPath("$.[*].marked").value(hasItem(DEFAULT_MARKED)));
    }

    @Test
    @Transactional
    public void getAttendenceStudent() throws Exception {
        // Initialize the database
        attendenceStudent = attendenceStudentRepository.saveAndFlush(attendenceStudent);

        // Get the attendenceStudent
        restAttendenceStudentMockMvc
            .perform(get("/api/attendence-students/{id}", attendenceStudent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(attendenceStudent.getId().intValue()))
            .andExpect(jsonPath("$.marked").value(DEFAULT_MARKED));
    }

    @Test
    @Transactional
    public void getNonExistingAttendenceStudent() throws Exception {
        // Get the attendenceStudent
        restAttendenceStudentMockMvc.perform(get("/api/attendence-students/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAttendenceStudent() throws Exception {
        // Initialize the database
        attendenceStudent = attendenceStudentRepository.saveAndFlush(attendenceStudent);

        int databaseSizeBeforeUpdate = attendenceStudentRepository.findAll().size();

        // Update the attendenceStudent
        AttendenceStudent updatedAttendenceStudent =
            attendenceStudentRepository.findById(attendenceStudent.getId()).get();
        // Disconnect from session so that the updates on updatedAttendenceStudent are not directly
        // saved in db
        em.detach(updatedAttendenceStudent);
        updatedAttendenceStudent.marked(UPDATED_MARKED);
        AttendenceStudentDTO attendenceStudentDTO =
            attendenceStudentMapper.toDto(updatedAttendenceStudent);

        restAttendenceStudentMockMvc
            .perform(put("/api/attendence-students").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(attendenceStudentDTO)))
            .andExpect(status().isOk());

        // Validate the AttendenceStudent in the database
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeUpdate);
        AttendenceStudent testAttendenceStudent =
            attendenceStudentList.get(attendenceStudentList.size() - 1);
        assertThat(testAttendenceStudent.getMarked()).isEqualTo(UPDATED_MARKED);
    }

    @Test
    @Transactional
    public void updateNonExistingAttendenceStudent() throws Exception {
        int databaseSizeBeforeUpdate = attendenceStudentRepository.findAll().size();

        // Create the AttendenceStudent
        AttendenceStudentDTO attendenceStudentDTO = attendenceStudentMapper
            .toDto(attendenceStudent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAttendenceStudentMockMvc
            .perform(put("/api/attendence-students").contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(attendenceStudentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AttendenceStudent in the database
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAttendenceStudent() throws Exception {
        // Initialize the database
        attendenceStudent = attendenceStudentRepository.saveAndFlush(attendenceStudent);

        int databaseSizeBeforeDelete = attendenceStudentRepository.findAll().size();

        // Delete the attendenceStudent
        restAttendenceStudentMockMvc
            .perform(delete("/api/attendence-students/{id}", attendenceStudent.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    @WithMockUser(username = DEFAULT_LOGIN, authorities = {AuthoritiesConstants.TEACHER})
    public void takeAttendenceStudent() throws Exception {
        int databaseSizeBeforeCreate = attendenceStudentRepository.findAll().size();
        List<AttendenceStudent> listOfStudent = getStudent();
        AttendenceStudentMapper mapper = new AttendenceStudentMapperImpl();
        List<AttendenceStudentDTO> listOfStudentDto = mapper.toDto(listOfStudent);
        restAttendenceStudentMockMvc.perform(post("/api/takeAttendence-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listOfStudentDto))
            .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isCreated());

        //check into db
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeCreate + 2);
        AttendenceStudent testAttendenceStudent =
            attendenceStudentList.get(attendenceStudentList.size() - 1);
        assertThat(testAttendenceStudent.getMarked()).isEqualTo(DEFAULT_MARKED);
    }

    @Test
    @Transactional
    @WithMockUser(username = DEFAULT_LOGIN, authorities = {AuthoritiesConstants.TEACHER})
    public void takeAttendenceStudentThrowAttendenceAlreadyTaken() throws Exception {
        List<AttendenceStudent> listOfStudent = getStudent();
        AttendenceStudentMapper mapper = new AttendenceStudentMapperImpl();
        List<AttendenceStudentDTO> listOfStudentDto = mapper.toDto(listOfStudent);
        attendenceStudentService.createAttendence(listOfStudentDto);
        int databaseSizeBeforeCreate = attendenceStudentRepository.findAll().size();
        restAttendenceStudentMockMvc.perform(post("/api/takeAttendence-student")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(listOfStudentDto))
            .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isBadRequest());

        //check into db
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeCreate);

    }

    @Test
    @Transactional
    @WithMockUser(username = DEFAULT_LOGIN, authorities = {AuthoritiesConstants.TEACHER})
    public void getAttendenceStudentByDate() throws Exception {
        List<AttendenceStudent> listOfStudent = getStudent();
        AttendenceStudentMapper mapper = new AttendenceStudentMapperImpl();
        List<AttendenceStudentDTO> listOfStudentDto = mapper.toDto(listOfStudent);
        log.info("dto:{},{}",listOfStudentDto,listOfStudent);
        attendenceStudentService.createAttendence(listOfStudentDto);
        int databaseSizeBeforeCreate = attendenceStudentRepository.findAll().size();
        restAttendenceStudentMockMvc.perform(get("/api/getAttendenceStudent")
            .param("date", LocalDate.now().toString())
            .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(2)));

        //check into db
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeCreate);

    }

    @Test
    @Transactional
    @WithMockUser(username = DEFAULT_LOGIN, authorities = {AuthoritiesConstants.TEACHER})
    public void getAttendencDateByTeacher() throws Exception {
        List<AttendenceStudent> listOfStudent = getStudent();
        AttendenceStudentMapper mapper = new AttendenceStudentMapperImpl();
        List<AttendenceStudentDTO> listOfStudentDto = mapper.toDto(listOfStudent);
        attendenceStudentService.createAttendence(listOfStudentDto);
        int databaseSizeBeforeCreate = attendenceStudentRepository.findAll().size();
        restAttendenceStudentMockMvc.perform(get("/api/getAttendenceDateByTeacher")
            .param("fromDate", LocalDate.now().toString())
            .param("toDate", LocalDate.now().toString())
            .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(1)));

        //check into db
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeCreate);

    }

    @Test
    @Transactional
    @WithMockUser(username = DEFAULT_LOGIN, authorities = {AuthoritiesConstants.TEACHER})
    public void getAttendencDateByTeacherButRecordsNotExist() throws Exception {
        List<AttendenceStudent> listOfStudent = getStudent();
        AttendenceStudentMapper mapper = new AttendenceStudentMapperImpl();
        List<AttendenceStudentDTO> listOfStudentDto = mapper.toDto(listOfStudent);
        attendenceStudentService.createAttendence(listOfStudentDto);
        LocalDate today = LocalDate.now();
        int databaseSizeBeforeCreate = attendenceStudentRepository.findAll().size();
        restAttendenceStudentMockMvc.perform(get("/api/getAttendenceDateByTeacher")
            .param("fromDate", today.minus(Period.ofDays(1)).toString())
            .param("toDate", today.minus(Period.ofDays(1)).toString())
            .accept(MediaType.APPLICATION_JSON)).andDo(MockMvcResultHandlers.print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()", is(0)));

        //check into db
        List<AttendenceStudent> attendenceStudentList = attendenceStudentRepository.findAll();
        assertThat(attendenceStudentList).hasSize(databaseSizeBeforeCreate);

    }

    private List<AttendenceStudent> getStudent() {
        User tempUser = new User();
        tempUser.setLogin("umang");
        tempUser.setPassword(RandomStringUtils.random(60));
        tempUser.setFirstName(UPDATED_FIRSTNAME);
        tempUser.setLastName(UPDATED_LASTNAME);
        tempUser.setEmail("umang@gmail.com");

        Authority authority = new Authority();
        authority.setName(AuthoritiesConstants.USER);
        tempUser.setAuthorities(new HashSet<Authority>(Arrays.asList(authority)));
        tempUser = userRepository.saveAndFlush(tempUser);
        log.info("tempUser:{},{}",tempUser.getId(),tempUser);
        log.info("tempUser:{},{}",user.getId(),user);
        log.info("user:{}",userRepository.findAll());
        AttendenceStudent attendenceStudent1 = new AttendenceStudent();
        AttendenceStudent attendenceStudent2 = new AttendenceStudent();
        attendenceStudent1.setMarked(DEFAULT_MARKED);
        attendenceStudent2.setMarked(DEFAULT_MARKED);

        User tempUser2 = new User();
        tempUser2.setId(tempUser.getId());
        User tempUser1 = new User();
        tempUser1.setId(user.getId());
        log.info("tempuser1:{},tempUser2:{}",user.getId(),tempUser.getId());
        attendenceStudent1.setUser(tempUser2);
        attendenceStudent2.setUser(tempUser1);

        List<AttendenceStudent> listOfAttendence = new ArrayList<>();
        listOfAttendence.add(attendenceStudent1);
        listOfAttendence.add(attendenceStudent2);
        return listOfAttendence;
    }

}
