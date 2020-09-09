package com.school.management.service;

import com.school.management.domain.Attendence;
import com.school.management.repository.AttendenceRepository;
import com.school.management.service.dto.AttendenceDTO;
import com.school.management.service.mapper.AttendenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Attendence}.
 */
@Service
@Transactional
public class AttendenceService {

    private final Logger log = LoggerFactory.getLogger(AttendenceService.class);

    private final AttendenceRepository attendenceRepository;

    private final AttendenceMapper attendenceMapper;

    public AttendenceService(AttendenceRepository attendenceRepository, AttendenceMapper attendenceMapper) {
        this.attendenceRepository = attendenceRepository;
        this.attendenceMapper = attendenceMapper;
    }

    /**
     * Save a attendence.
     *
     * @param attendenceDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendenceDTO save(AttendenceDTO attendenceDTO) {
        log.debug("Request to save Attendence : {}", attendenceDTO);
        Attendence attendence = attendenceMapper.toEntity(attendenceDTO);
        attendence = attendenceRepository.save(attendence);
        return attendenceMapper.toDto(attendence);
    }

    /**
     * Get all the attendences.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendenceDTO> findAll() {
        log.debug("Request to get all Attendences");
        return attendenceRepository.findAll().stream()
            .map(attendenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one attendence by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendenceDTO> findOne(Long id) {
        log.debug("Request to get Attendence : {}", id);
        return attendenceRepository.findById(id)
            .map(attendenceMapper::toDto);
    }

    /**
     * Delete the attendence by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Attendence : {}", id);
        attendenceRepository.deleteById(id);
    }
}
