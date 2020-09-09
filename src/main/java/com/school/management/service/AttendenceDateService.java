package com.school.management.service;

import com.school.management.domain.AttendenceDate;
import com.school.management.repository.AttendenceDateRepository;
import com.school.management.service.dto.AttendenceDateDTO;
import com.school.management.service.mapper.AttendenceDateMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link AttendenceDate}.
 */
@Service
@Transactional
public class AttendenceDateService {

    private final Logger log = LoggerFactory.getLogger(AttendenceDateService.class);

    private final AttendenceDateRepository attendenceDateRepository;

    private final AttendenceDateMapper attendenceDateMapper;

    public AttendenceDateService(AttendenceDateRepository attendenceDateRepository, AttendenceDateMapper attendenceDateMapper) {
        this.attendenceDateRepository = attendenceDateRepository;
        this.attendenceDateMapper = attendenceDateMapper;
    }

    /**
     * Save a attendenceDate.
     *
     * @param attendenceDateDTO the entity to save.
     * @return the persisted entity.
     */
    public AttendenceDateDTO save(AttendenceDateDTO attendenceDateDTO) {
        log.debug("Request to save AttendenceDate : {}", attendenceDateDTO);
        AttendenceDate attendenceDate = attendenceDateMapper.toEntity(attendenceDateDTO);
        attendenceDate = attendenceDateRepository.save(attendenceDate);
        return attendenceDateMapper.toDto(attendenceDate);
    }

    /**
     * Get all the attendenceDates.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<AttendenceDateDTO> findAll() {
        log.debug("Request to get all AttendenceDates");
        return attendenceDateRepository.findAll().stream()
            .map(attendenceDateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one attendenceDate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<AttendenceDateDTO> findOne(Long id) {
        log.debug("Request to get AttendenceDate : {}", id);
        return attendenceDateRepository.findById(id)
            .map(attendenceDateMapper::toDto);
    }

    /**
     * Delete the attendenceDate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete AttendenceDate : {}", id);
        attendenceDateRepository.deleteById(id);
    }
}
