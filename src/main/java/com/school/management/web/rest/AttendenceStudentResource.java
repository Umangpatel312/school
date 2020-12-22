package com.school.management.web.rest;

import com.school.management.service.dto.AttendenceDateDTO;
import io.github.jhipster.web.util.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.school.management.service.AttendenceStudentService;
import com.school.management.service.dto.AttendenceStudentDTO;
import com.school.management.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link com.school.management.domain.AttendenceStudent}.
 */
@RestController
@RequestMapping("/api")
public class AttendenceStudentResource {

  private final Logger log = LoggerFactory.getLogger(AttendenceStudentResource.class);

  private static final String ENTITY_NAME = "attendenceStudent";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final AttendenceStudentService attendenceStudentService;

  public AttendenceStudentResource(AttendenceStudentService attendenceStudentService) {
    this.attendenceStudentService = attendenceStudentService;
  }

  /**
   * {@code POST  /attendence-students} : Create a new attendenceStudent.
   *
   * @param attendenceStudentDTO the attendenceStudentDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new
   *         attendenceStudentDTO, or with status {@code 400 (Bad Request)} if the attendenceStudent
   *         has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/attendence-students")
  public ResponseEntity<AttendenceStudentDTO> createAttendenceStudent(
      @RequestBody AttendenceStudentDTO attendenceStudentDTO) throws URISyntaxException {
    log.debug("REST request to save AttendenceStudent : {}", attendenceStudentDTO);
    if (attendenceStudentDTO.getId() != null) {
      throw new BadRequestAlertException("A new attendenceStudent cannot already have an ID",
          ENTITY_NAME, "idexists");
    }
    AttendenceStudentDTO result = attendenceStudentService.save(attendenceStudentDTO);
    return ResponseEntity.created(new URI("/api/attendence-students/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME,
            result.getId().toString()))
        .body(result);
  }

  /**
   * {@code PUT  /attendence-students} : Updates an existing attendenceStudent.
   *
   * @param attendenceStudentDTO the attendenceStudentDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated
   *         attendenceStudentDTO, or with status {@code 400 (Bad Request)} if the
   *         attendenceStudentDTO is not valid, or with status {@code 500 (Internal Server Error)}
   *         if the attendenceStudentDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/attendence-students")
  public ResponseEntity<AttendenceStudentDTO> updateAttendenceStudent(
      @RequestBody AttendenceStudentDTO attendenceStudentDTO) throws URISyntaxException {
    log.debug("REST request to update AttendenceStudent : {}", attendenceStudentDTO);
    if (attendenceStudentDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    AttendenceStudentDTO result = attendenceStudentService.save(attendenceStudentDTO);
    return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(applicationName, false,
        ENTITY_NAME, attendenceStudentDTO.getId().toString())).body(result);
  }

  /**
   * {@code GET  /attendence-students} : get all the attendenceStudents.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of
   *         attendenceStudents in body.
   */
  @GetMapping("/attendence-students")
  public List<AttendenceStudentDTO> getAllAttendenceStudents() {
    log.debug("REST request to get all AttendenceStudents");
    return attendenceStudentService.findAll();
  }

  /**
   * {@code GET  /attendence-students/:id} : get the "id" attendenceStudent.
   *
   * @param id the id of the attendenceStudentDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the
   *         attendenceStudentDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/attendence-students/{id}")
  public ResponseEntity<AttendenceStudentDTO> getAttendenceStudent(@PathVariable Long id) {
    log.debug("REST request to get AttendenceStudent : {}", id);
    Optional<AttendenceStudentDTO> attendenceStudentDTO = attendenceStudentService.findOne(id);
    return ResponseUtil.wrapOrNotFound(attendenceStudentDTO);
  }

  /**
   * {@code DELETE  /attendence-students/:id} : delete the "id" attendenceStudent.
   *
   * @param id the id of the attendenceStudentDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/attendence-students/{id}")
  public ResponseEntity<Void> deleteAttendenceStudent(@PathVariable Long id) {
    log.debug("REST request to delete AttendenceStudent : {}", id);
    attendenceStudentService.delete(id);
    return ResponseEntity.noContent().headers(
        HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
        .build();
  }

  /*
   * custom api here
   */
  @PostMapping("/takeAttendence-student")
  public ResponseEntity<List<AttendenceStudentDTO>> attendenceStudent(
      @RequestBody List<AttendenceStudentDTO> attendenceStudentDTO) {
    List<AttendenceStudentDTO> listOfStudentDTO =
        attendenceStudentService.createAttendence(attendenceStudentDTO);
    return new ResponseEntity<>(listOfStudentDTO, HttpStatus.CREATED);
  }

  @GetMapping("/getAttendenceStudent")
  public ResponseEntity<List<AttendenceStudentDTO>> getAttendenceStudentByDate(@RequestParam("date") LocalDate localDate) {
    log.info("date;{}", localDate);

    List<AttendenceStudentDTO> listOfDTO = attendenceStudentService.findByDate(localDate);
    return new ResponseEntity<>(listOfDTO, HttpStatus.OK);
  }

    @GetMapping("/getAttendenceDateByTeacher")
    public ResponseEntity<List<AttendenceDateDTO>> getDatesByTeacher(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate,
        Pageable pageable) {
        log.info("from date:{}", fromDate);
        log.info("to date:{}", toDate);

        Page<AttendenceDateDTO> page = attendenceStudentService.findDateByTeacher(fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil
            .generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @GetMapping("/getAttendencesByStudent")
    public ResponseEntity<List<AttendenceStudentDTO>> getAttendenceByStudent(
        @RequestParam(value = "fromDate") LocalDate fromDate,
        @RequestParam(value = "toDate") LocalDate toDate
    ){
      List<AttendenceStudentDTO> listOfStudent=attendenceStudentService.findByStudent(fromDate,toDate);
      return new ResponseEntity<>(listOfStudent,HttpStatus.OK);
    }

}
