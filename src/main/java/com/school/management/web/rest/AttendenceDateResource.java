package com.school.management.web.rest;

import com.school.management.domain.Attendence;
import com.school.management.domain.AttendenceDate;
import com.school.management.service.AttendenceDateService;
import com.school.management.web.rest.errors.BadRequestAlertException;
import com.school.management.service.dto.AttendenceDateDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.school.management.domain.AttendenceDate}.
 */
@RestController
@RequestMapping("/api")
public class AttendenceDateResource {

    private final Logger log = LoggerFactory.getLogger(AttendenceDateResource.class);

    private static final String ENTITY_NAME = "attendenceDate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendenceDateService attendenceDateService;

    public AttendenceDateResource(AttendenceDateService attendenceDateService) {
        this.attendenceDateService = attendenceDateService;
    }

    /**
     * {@code POST  /attendence-dates} : Create a new attendenceDate.
     *
     * @param attendenceDateDTO the attendenceDateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendenceDateDTO, or with status {@code 400 (Bad Request)} if the attendenceDate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendence-dates")
    public ResponseEntity<AttendenceDateDTO> createAttendenceDate(@RequestBody AttendenceDateDTO attendenceDateDTO) throws URISyntaxException {
        log.debug("REST request to save AttendenceDate : {}", attendenceDateDTO);
        if (attendenceDateDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendenceDate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendenceDateDTO result = attendenceDateService.save(attendenceDateDTO);
        return ResponseEntity.created(new URI("/api/attendence-dates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendence-dates} : Updates an existing attendenceDate.
     *
     * @param attendenceDateDTO the attendenceDateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendenceDateDTO,
     * or with status {@code 400 (Bad Request)} if the attendenceDateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendenceDateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendence-dates")
    public ResponseEntity<AttendenceDateDTO> updateAttendenceDate(@RequestBody AttendenceDateDTO attendenceDateDTO) throws URISyntaxException {
        log.debug("REST request to update AttendenceDate : {}", attendenceDateDTO);
        if (attendenceDateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendenceDateDTO result = attendenceDateService.save(attendenceDateDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendenceDateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendence-dates} : get all the attendenceDates.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendenceDates in body.
     */
    @GetMapping("/attendence-dates")
    public List<AttendenceDateDTO> getAllAttendenceDates() {
        log.debug("REST request to get all AttendenceDates");
        return attendenceDateService.findAll();
    }

    /**
     * {@code GET  /attendence-dates/:id} : get the "id" attendenceDate.
     *
     * @param id the id of the attendenceDateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendenceDateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendence-dates/{id}")
    public ResponseEntity<AttendenceDateDTO> getAttendenceDate(@PathVariable Long id) {
        log.debug("REST request to get AttendenceDate : {}", id);
        Optional<AttendenceDateDTO> attendenceDateDTO = attendenceDateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendenceDateDTO);
    }

    /**
     * {@code DELETE  /attendence-dates/:id} : delete the "id" attendenceDate.
     *
     * @param id the id of the attendenceDateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendence-dates/{id}")
    public ResponseEntity<Void> deleteAttendenceDate(@PathVariable Long id) {
        log.debug("REST request to delete AttendenceDate : {}", id);
        attendenceDateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
