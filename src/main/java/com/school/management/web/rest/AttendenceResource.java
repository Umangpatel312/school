package com.school.management.web.rest;

import com.school.management.service.AttendenceService;
import com.school.management.web.rest.errors.BadRequestAlertException;
import com.school.management.service.dto.AttendenceDTO;

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
 * REST controller for managing {@link com.school.management.domain.Attendence}.
 */
@RestController
@RequestMapping("/api")
public class AttendenceResource {

    private final Logger log = LoggerFactory.getLogger(AttendenceResource.class);

    private static final String ENTITY_NAME = "attendence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttendenceService attendenceService;

    public AttendenceResource(AttendenceService attendenceService) {
        this.attendenceService = attendenceService;
    }

    /**
     * {@code POST  /attendences} : Create a new attendence.
     *
     * @param attendenceDTO the attendenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new attendenceDTO, or with status {@code 400 (Bad Request)} if the attendence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/attendences")
    public ResponseEntity<AttendenceDTO> createAttendence(@RequestBody AttendenceDTO attendenceDTO) throws URISyntaxException {
        log.debug("REST request to save Attendence : {}", attendenceDTO);
        if (attendenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new attendence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttendenceDTO result = attendenceService.save(attendenceDTO);
        return ResponseEntity.created(new URI("/api/attendences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /attendences} : Updates an existing attendence.
     *
     * @param attendenceDTO the attendenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated attendenceDTO,
     * or with status {@code 400 (Bad Request)} if the attendenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the attendenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/attendences")
    public ResponseEntity<AttendenceDTO> updateAttendence(@RequestBody AttendenceDTO attendenceDTO) throws URISyntaxException {
        log.debug("REST request to update Attendence : {}", attendenceDTO);
        if (attendenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttendenceDTO result = attendenceService.save(attendenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, attendenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /attendences} : get all the attendences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of attendences in body.
     */
    @GetMapping("/attendences")
    public List<AttendenceDTO> getAllAttendences() {
        log.debug("REST request to get all Attendences");
        return attendenceService.findAll();
    }

    /**
     * {@code GET  /attendences/:id} : get the "id" attendence.
     *
     * @param id the id of the attendenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the attendenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/attendences/{id}")
    public ResponseEntity<AttendenceDTO> getAttendence(@PathVariable Long id) {
        log.debug("REST request to get Attendence : {}", id);
        Optional<AttendenceDTO> attendenceDTO = attendenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attendenceDTO);
    }

    /**
     * {@code DELETE  /attendences/:id} : delete the "id" attendence.
     *
     * @param id the id of the attendenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/attendences/{id}")
    public ResponseEntity<Void> deleteAttendence(@PathVariable Long id) {
        log.debug("REST request to delete Attendence : {}", id);
        attendenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
