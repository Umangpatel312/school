package com.school.management.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
import org.springframework.transaction.annotation.Transactional;
import com.school.management.SchoolApp;
import com.school.management.domain.Authority;
import com.school.management.domain.Grade;
import com.school.management.domain.GradeStudent;
import com.school.management.domain.GradeTeacher;
import com.school.management.domain.User;
import com.school.management.repository.GradeRepository;
import com.school.management.repository.GradeStudentRepository;
import com.school.management.repository.GradeTeacherRepository;
import com.school.management.repository.UserRepository;
import com.school.management.security.AuthoritiesConstants;
import com.school.management.service.GradeStudentService;
import com.school.management.service.dto.GradeStudentDTO;
import com.school.management.service.mapper.GradeStudentMapper;

/**
 * Integration tests for the {@link GradeStudentResource} REST controller.
 */
@SpringBootTest(classes = SchoolApp.class)
@AutoConfigureMockMvc
@WithMockUser(username = "admin", authorities = {AuthoritiesConstants.ADMIN})
public class GradeStudentResourceIT {
  private final static Logger log = LoggerFactory.getLogger(UserResourceIT.class);

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

  @Autowired
  private GradeStudentRepository gradeStudentRepository;

  @Autowired
  private GradeStudentMapper gradeStudentMapper;

  @Autowired
  private GradeStudentService gradeStudentService;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restGradeStudentMockMvc;

  private GradeStudent gradeStudent;
  private User user;
  private Grade grade;
  private GradeTeacher gradeTeacher;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private GradeTeacherRepository gradeTeacherRepository;
  @Autowired
  private GradeRepository gradeRepository;
  @Autowired
  private CacheManager cacheManager;



  /**
   * Create an entity for this test.
   *
   * This is a static method, as tests for other entities might also need it, if they test an entity
   * which requires the current entity.
   */
  public static GradeStudent createEntity(EntityManager em) {
    Grade grade = new Grade();
    User user = new User();
    GradeStudent gradeStudent = new GradeStudent();
    gradeStudent.setGrade(grade);
    gradeStudent.setUser(user);
    return gradeStudent;
  }

  /**
   * Create an updated entity for this test.
   *
   * This is a static method, as tests for other entities might also need it, if they test an entity
   * which requires the current entity.
   */
  @BeforeEach
  @Transactional
  public void setup() {
    cacheManager.getCache(UserRepository.USERS_BY_LOGIN_CACHE).clear();
    cacheManager.getCache(UserRepository.USERS_BY_EMAIL_CACHE).clear();
    User user = new User();
    user.setLogin(DEFAULT_LOGIN);
    user.setEmail(DEFAULT_EMAIL);
    user.setPassword(RandomStringUtils.random(60));
    user.setActivated(true);
    Authority au2 = new Authority();
    au2.setName(AuthoritiesConstants.USER);
    user.setAuthorities(new HashSet<Authority>(Arrays.asList(au2)));
    userRepository.save(user);
    Grade grade = new Grade();
    grade.setGrade(8);
    gradeRepository.save(grade);
  }

  public static GradeStudent createUpdatedEntity(EntityManager em) {
    GradeStudent gradeStudent = new GradeStudent();

    return gradeStudent;
  }

  @BeforeEach
  public void initTest() {
    gradeStudent = createEntity(em);
    user = new User();
    grade = new Grade();
    User tempUser = userRepository.findOneByLogin(DEFAULT_LOGIN).get();
    user.setId(tempUser.getId());
    gradeStudent.setUser(user);
    Grade tempGrade = gradeRepository.findByGrade(8);
    grade.setId(tempGrade.getId());
    gradeStudent.setGrade(grade);
  }

  @Test
  @Transactional
  public void createGradeStudent() throws Exception {
    int databaseSizeBeforeCreate = gradeStudentRepository.findAll().size();
    // Create the GradeStudent
    // User tempUser = userRepository.findOneByLogin(DEFAULT_LOGIN).get();
    // user.setId(tempUser.getId());
    // gradeStudent.setUser(user);
    // Grade tempGrade = gradeRepository.findByGrade(8);
    // grade.setId(tempGrade.getId());
    // gradeStudent.setGrade(grade);
    GradeStudentDTO gradeStudentDTO = gradeStudentMapper.toDto(gradeStudent);
    restGradeStudentMockMvc
        .perform(post("/api/grade-students").contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeStudentDTO)))
        .andExpect(status().isCreated());

    // Validate the GradeStudent in the database
    List<GradeStudent> gradeStudentList = gradeStudentRepository.findAll();
    assertThat(gradeStudentList).hasSize(databaseSizeBeforeCreate + 1);
    GradeStudent testGradeStudent = gradeStudentList.get(gradeStudentList.size() - 1);
  }

  @Test
  @Transactional
  public void createGradeStudentWithExistingId() throws Exception {


    gradeStudent = gradeStudentRepository.save(gradeStudent);
    int databaseSizeBeforeCreate = gradeStudentRepository.findAll().size();
    // Create the GradeStudent with an existing ID
    gradeStudent.setId(gradeStudent.getId());
    GradeStudentDTO gradeStudentDTO = gradeStudentMapper.toDto(gradeStudent);

    // An entity with an existing ID cannot be created, so this API call must fail
    restGradeStudentMockMvc
        .perform(post("/api/grade-students").contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeStudentDTO)))
        .andExpect(status().isBadRequest());

    // Validate the GradeStudent in the database
    List<GradeStudent> gradeStudentList = gradeStudentRepository.findAll();
    assertThat(gradeStudentList).hasSize(databaseSizeBeforeCreate);
  }


  @Test
  @Transactional
  public void getAllGradeStudents() throws Exception {
    // Initialize the database
    gradeStudentRepository.saveAndFlush(gradeStudent);

    // Get all the gradeStudentList
    restGradeStudentMockMvc.perform(get("/api/grade-students?sort=id,desc"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.[*].id").value(hasItem(gradeStudent.getId().intValue())));
  }

  @Test
  @Transactional
  public void getGradeStudent() throws Exception {
    // Initialize the database

    gradeStudentRepository.saveAndFlush(gradeStudent);

    // Get the gradeStudent
    restGradeStudentMockMvc.perform(get("/api/grade-students/{id}", gradeStudent.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(gradeStudent.getId().intValue()));
  }

  @Test
  @Transactional
  public void getNonExistingGradeStudent() throws Exception {
    // Get the gradeStudent
    restGradeStudentMockMvc.perform(get("/api/grade-students/{id}", Long.MAX_VALUE))
        .andExpect(status().isNotFound());
  }

  @Test
  @Transactional
  public void updateGradeStudent() throws Exception {
    // Initialize the database

    GradeStudent gStudent = gradeStudentRepository.saveAndFlush(gradeStudent);
    log.info("return user:{}", gStudent.getUser());
    int databaseSizeBeforeUpdate = gradeStudentRepository.findAll().size();

    // Update the gradeStudent
    GradeStudent updatedGradeStudent = gradeStudentRepository.findById(gStudent.getId()).get();
    // Disconnect from session so that the updates on updatedGradeStudent are not directly saved in
    // db

    em.detach(updatedGradeStudent);
    GradeStudentDTO gradeStudentDTO = gradeStudentMapper.toDto(updatedGradeStudent);
    GradeStudent gradeStudent1 = gradeStudentMapper.toEntity(gradeStudentDTO);
    log.info("detach:{}", gradeStudent1.getUser().getId());
    log.info("detach:{}", gradeStudent1.getUser());
    restGradeStudentMockMvc
        .perform(put("/api/grade-students").contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeStudentDTO)))
        .andExpect(status().isOk());

    // Validate the GradeStudent in the database
    List<GradeStudent> gradeStudentList = gradeStudentRepository.findAll();
    log.info("list of:{}", gradeStudentList);
    assertThat(gradeStudentList).hasSize(databaseSizeBeforeUpdate);
    GradeStudent testGradeStudent = gradeStudentList.get(gradeStudentList.size() - 1);
  }

  @Test
  @Transactional
  public void updateNonExistingGradeStudent() throws Exception {
    int databaseSizeBeforeUpdate = gradeStudentRepository.findAll().size();

    // Create the GradeStudent
    GradeStudentDTO gradeStudentDTO = gradeStudentMapper.toDto(gradeStudent);

    // If the entity doesn't have an ID, it will throw BadRequestAlertException
    restGradeStudentMockMvc
        .perform(put("/api/grade-students").contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(gradeStudentDTO)))
        .andExpect(status().isBadRequest());

    // Validate the GradeStudent in the database
    List<GradeStudent> gradeStudentList = gradeStudentRepository.findAll();
    assertThat(gradeStudentList).hasSize(databaseSizeBeforeUpdate);
  }

  @Test
  @Transactional
  public void deleteGradeStudent() throws Exception {
    // Initialize the database
    gradeStudentRepository.saveAndFlush(gradeStudent);

    int databaseSizeBeforeDelete = gradeStudentRepository.findAll().size();
    log.info("before delete student:{}", databaseSizeBeforeDelete);
    // Delete the gradeStudent
    restGradeStudentMockMvc.perform(
        delete("/api/grade-students/{id}", gradeStudent.getId()).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Validate the database contains one less item
    List<GradeStudent> gradeStudentList = gradeStudentRepository.findAll();
    assertThat(gradeStudentList).hasSize(databaseSizeBeforeDelete - 1);
  }

  /*
   * new test cases
   */

  @Test
  @Transactional
  @WithMockUser(username = "umang", authorities = AuthoritiesConstants.TEACHER)
  public void getStudentByTeacher() throws Exception {

    addStudentAndTeacherToDB();
    // Get the gradeStudent
    restGradeStudentMockMvc.perform(get("/api/getStudentByTeacher")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.length()", is(1)));
  }

  @Test
  @Transactional
  @WithMockUser(username = "xyz", authorities = AuthoritiesConstants.TEACHER)
  public void getStudentByTeacherFoundZero() throws Exception {

    addStudentAndTeacherToDB();
    // Get the gradeStudent
    restGradeStudentMockMvc.perform(get("/api/getStudentByTeacher")).andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.length()", is(0)));
  }

  @Test
  @Transactional
  public void getStudentByGrade() throws Exception {

    addStudentAndTeacherToDB();
    // Get the gradeStudent
    restGradeStudentMockMvc.perform(get("/api/getStudentByGrade/{gradeId}", 8))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.length()", is(1)));
  }


  private void addStudentAndTeacherToDB() {

    User tempUser = new User();
    GradeStudent gradeStudent = new GradeStudent();
    Grade grade = null;
    Grade tempGrade = gradeRepository.findByGrade(8);
    tempUser.setLogin(UPDATED_LOGIN);
    tempUser.setPassword(RandomStringUtils.random(60));
    tempUser.setFirstName(UPDATED_FIRSTNAME);
    tempUser.setLastName(UPDATED_LASTNAME);
    tempUser.setEmail(UPDATED_EMAIL);
    tempUser.setImageUrl(UPDATED_IMAGEURL);
    tempUser.setLangKey(UPDATED_LANGKEY);
    Authority au = new Authority();
    au.setName(AuthoritiesConstants.USER);
    tempUser.setAuthorities(new HashSet<Authority>(Arrays.asList(au)));
    tempUser = userRepository.save(tempUser);
    gradeStudent = new GradeStudent();
    User user = new User();
    user.setId(tempUser.getId());
    gradeStudent.setUser(user);
    grade = new Grade();
    grade.setId(tempGrade.getId());
    gradeStudent.setGrade(grade);
    gradeStudentRepository.saveAndFlush(gradeStudent);


    // creating teacher
    tempUser = new User();

    tempUser.setLogin("umang");
    tempUser.setPassword(RandomStringUtils.random(60));
    tempUser.setFirstName("umang");
    tempUser.setLastName("patel");
    tempUser.setEmail("umang@gmail.com");
    tempUser.setImageUrl(UPDATED_IMAGEURL);
    tempUser.setLangKey(UPDATED_LANGKEY);
    au = new Authority();
    Authority au2 = new Authority();
    au.setName(AuthoritiesConstants.USER);
    au2.setName(AuthoritiesConstants.TEACHER);
    tempUser.setAuthorities(new HashSet<Authority>(Arrays.asList(au, au2)));
    tempUser = userRepository.save(tempUser);
    user = new User();
    user.setId(tempUser.getId());
    grade = new Grade();
    Grade tempGrade2 = gradeRepository.findByGrade(8);
    grade.setId(tempGrade2.getId());
    GradeTeacher gradeTeacher = new GradeTeacher();
    gradeTeacher.setUser(user);
    gradeTeacher.setGrade(grade);
    gradeTeacherRepository.saveAndFlush(gradeTeacher);
  }


}
