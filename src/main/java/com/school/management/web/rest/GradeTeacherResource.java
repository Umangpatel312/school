package com.school.management.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.school.management.service.GradeTeacherService;
import com.school.management.service.dto.GradeTeacherDTO;
import com.school.management.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.school.management.domain.GradeTeacher}.
 */
@RestController
@RequestMapping("/api")
public class GradeTeacherResource {

  private final Logger log = LoggerFactory.getLogger(GradeTeacherResource.class);

  private static final String ENTITY_NAME = "gradeTeacher";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final GradeTeacherService gradeTeacherService;

  public GradeTeacherResource(GradeTeacherService gradeTeacherService) {
    this.gradeTeacherService = gradeTeacherService;
  }

  /**
   * {@code POST  /grade-teachers} : Create a new gradeTeacher.
   *
   * @param gradeTeacherDTO the gradeTeacherDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *         gradeTeacherDTO, or with status {@code 400 (Bad Request)} if the gradeTeacher has
   *         already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/grade-teachers")
  public ResponseEntity<GradeTeacherDTO> createGradeTeacher(
      @RequestBody GradeTeacherDTO gradeTeacherDTO) throws URISyntaxException {
    log.debug("REST request to save GradeTeacher : {}", gradeTeacherDTO);
    if (gradeTeacherDTO.getId() != null) {
      throw new BadRequestAlertException("A new gradeTeacher cannot already have an ID",
          ENTITY_NAME, "idexists");
    }
    GradeTeacherDTO result = gradeTeacherService.save(gradeTeacherDTO);
    return ResponseEntity.created(new URI("/api/grade-teachers/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
            result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /grade-teachers} : Updates an existing gradeTeacher.
   *
   * @param gradeTeacherDTO the gradeTeacherDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *         gradeTeacherDTO, or with status {@code 400 (Bad Request)} if the gradeTeacherDTO is not
   *         valid, or with status {@code 500 (Internal Server Error)} if the gradeTeacherDTO
   *         couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/grade-teachers")
  public ResponseEntity<GradeTeacherDTO> updateGradeTeacher(
      @RequestBody GradeTeacherDTO gradeTeacherDTO) throws URISyntaxException {
    log.debug("REST request to update GradeTeacher : {}", gradeTeacherDTO);
    if (gradeTeacherDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    GradeTeacherDTO result = gradeTeacherService.save(gradeTeacherDTO);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false,
        ENTITY_NAME, gradeTeacherDTO.getId().toString())).body(result);
  }

  /**
   * {@code GET  /grade-teachers} : get all the gradeTeachers.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gradeTeachers
   *         in body.
   */
  @GetMapping("/grade-teachers")
  public List<GradeTeacherDTO> getAllGradeTeachers() {
    log.debug("REST request to get all GradeTeachers");
    return gradeTeacherService.findAll();
  }

  /**
   * {@code GET  /grade-teachers/:id} : get the "id" gradeTeacher.
   *
   * @param id the id of the gradeTeacherDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *         gradeTeacherDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/grade-teachers/{id}")
  public ResponseEntity<GradeTeacherDTO> getGradeTeacher(@PathVariable Long id) {
    log.debug("REST request to get GradeTeacher : {}", id);
    Optional<GradeTeacherDTO> gradeTeacherDTO = gradeTeacherService.findOne(id);
    return ResponseUtil.wrapOrNotFound(gradeTeacherDTO);
  }

  /**
   * {@code DELETE  /grade-teachers/:id} : delete the "id" gradeTeacher.
   *
   * @param id the id of the gradeTeacherDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/grade-teachers/{id}")
  public ResponseEntity<Void> deleteGradeTeacher(@PathVariable Long id) {
    log.debug("REST request to delete GradeTeacher : {}", id);
    gradeTeacherService.delete(id);
    return ResponseEntity.noContent().headers(
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }
}
