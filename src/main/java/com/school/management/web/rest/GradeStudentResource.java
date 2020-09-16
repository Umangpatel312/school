package com.school.management.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.school.management.service.GradeStudentService;
import com.school.management.service.dto.GradeStudentDTO;
import com.school.management.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.school.management.domain.GradeStudent}.
 */
@RestController
@RequestMapping("/api")
public class GradeStudentResource {

  private final Logger log = LoggerFactory.getLogger(GradeStudentResource.class);

  private static final String ENTITY_NAME = "gradeStudent";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final GradeStudentService gradeStudentService;

  public GradeStudentResource(GradeStudentService gradeStudentService) {
    this.gradeStudentService = gradeStudentService;
  }

  /**
   * {@code POST  /grade-students} : Create a new gradeStudent.
   *
   * @param gradeStudentDTO the gradeStudentDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *         gradeStudentDTO, or with status {@code 400 (Bad Request)} if the gradeStudent has
   *         already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/grade-students")
  public ResponseEntity<GradeStudentDTO> createGradeStudent(
      @RequestBody GradeStudentDTO gradeStudentDTO) throws URISyntaxException {
    log.debug("REST request to save GradeStudent : {}", gradeStudentDTO);
    if (gradeStudentDTO.getId() != null) {
      throw new BadRequestAlertException("A new gradeStudent cannot already have an ID",
          ENTITY_NAME, "idexists");
    }
    GradeStudentDTO result = gradeStudentService.save(gradeStudentDTO);
    return ResponseEntity.created(new URI("/api/grade-students/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
            result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /grade-students} : Updates an existing gradeStudent.
   *
   * @param gradeStudentDTO the gradeStudentDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *         gradeStudentDTO, or with status {@code 400 (Bad Request)} if the gradeStudentDTO is not
   *         valid, or with status {@code 500 (Internal Server Error)} if the gradeStudentDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/grade-students")
  public ResponseEntity<GradeStudentDTO> updateGradeStudent(
      @RequestBody GradeStudentDTO gradeStudentDTO) throws URISyntaxException {
    log.debug("REST request to update GradeStudent : {}", gradeStudentDTO);
    if (gradeStudentDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    GradeStudentDTO result = gradeStudentService.save(gradeStudentDTO);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false,
        ENTITY_NAME, gradeStudentDTO.getId().toString())).body(result);
  }

  /**
   * {@code GET  /grade-students} : get all the gradeStudents.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gradeStudents
   *         in body.
   */
  @GetMapping("/grade-students")
  public List<GradeStudentDTO> getAllGradeStudents() {
    log.debug("REST request to get all GradeStudents");
    return gradeStudentService.findAll();
  }

  /**
   * {@code GET  /grade-students/:id} : get the "id" gradeStudent.
   *
   * @param id the id of the gradeStudentDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *         gradeStudentDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/grade-students/{id}")
  public ResponseEntity<GradeStudentDTO> getGradeStudent(@PathVariable Long id) {
    log.debug("REST request to get GradeStudent : {}", id);
    Optional<GradeStudentDTO> gradeStudentDTO = gradeStudentService.findOne(id);
    return ResponseUtil.wrapOrNotFound(gradeStudentDTO);
  }

  /**
   * {@code DELETE  /grade-students/:id} : delete the "id" gradeStudent.
   *
   * @param id the id of the gradeStudentDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/grade-students/{id}")
  public ResponseEntity<Void> deleteGradeStudent(@PathVariable Long id) {
    log.debug("REST request to delete GradeStudent : {}", id);
    gradeStudentService.delete(id);
    return ResponseEntity.noContent().headers(
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }


  /*
   * as per required rest api started here
   */
  @GetMapping("/getStudentByTeacher")
  public ResponseEntity<List<GradeStudentDTO>> getStudentByTeacher() {
    log.debug("rest request get student by their grade: ");
    List<GradeStudentDTO> listOfDto = gradeStudentService.findByTeacher();
    return new ResponseEntity<List<GradeStudentDTO>>(listOfDto, HttpStatus.OK);
  }

  @GetMapping("/getStudentByGrade/{grade}")
  public ResponseEntity<List<GradeStudentDTO>> getGradeStudent(@PathVariable int grade) {
    log.debug("rest request get student by their grade: {}", grade);
    List<GradeStudentDTO> listOfDto = gradeStudentService.findByGrade(grade);
    return new ResponseEntity<List<GradeStudentDTO>>(listOfDto, HttpStatus.OK);
  }

}
