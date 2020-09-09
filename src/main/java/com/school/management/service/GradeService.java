package com.school.management.service;

import com.school.management.domain.Grade;
import com.school.management.repository.GradeRepository;
import com.school.management.service.dto.GradeDTO;
import com.school.management.service.mapper.GradeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Grade}.
 */
@Service
@Transactional
public class GradeService {

    private final Logger log = LoggerFactory.getLogger(GradeService.class);

    private final GradeRepository gradeRepository;

    private final GradeMapper gradeMapper;

    public GradeService(GradeRepository gradeRepository, GradeMapper gradeMapper) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
    }

    /**
     * Save a grade.
     *
     * @param gradeDTO the entity to save.
     * @return the persisted entity.
     */
    public GradeDTO save(GradeDTO gradeDTO) {
        log.debug("Request to save Grade : {}", gradeDTO);
        Grade grade = gradeMapper.toEntity(gradeDTO);
        grade = gradeRepository.save(grade);
        return gradeMapper.toDto(grade);
    }

    /**
     * Get all the grades.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GradeDTO> findAll() {
        log.debug("Request to get all Grades");
        return gradeRepository.findAll().stream()
            .map(gradeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one grade by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GradeDTO> findOne(Long id) {
        log.debug("Request to get Grade : {}", id);
        return gradeRepository.findById(id)
            .map(gradeMapper::toDto);
    }

    /**
     * Delete the grade by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Grade : {}", id);
        gradeRepository.deleteById(id);
    }
}
