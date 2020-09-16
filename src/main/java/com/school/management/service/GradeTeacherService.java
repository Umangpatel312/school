package com.school.management.service;

import com.school.management.domain.GradeTeacher;
import com.school.management.repository.GradeTeacherRepository;
import com.school.management.service.dto.GradeTeacherDTO;
import com.school.management.service.mapper.GradeTeacherMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link GradeTeacher}.
 */
@Service
@Transactional
public class GradeTeacherService {

    private final Logger log = LoggerFactory.getLogger(GradeTeacherService.class);

    private final GradeTeacherRepository gradeTeacherRepository;

    private final GradeTeacherMapper gradeTeacherMapper;

    public GradeTeacherService(GradeTeacherRepository gradeTeacherRepository, GradeTeacherMapper gradeTeacherMapper) {
        this.gradeTeacherRepository = gradeTeacherRepository;
        this.gradeTeacherMapper = gradeTeacherMapper;
    }

    /**
     * Save a gradeTeacher.
     *
     * @param gradeTeacherDTO the entity to save.
     * @return the persisted entity.
     */
    public GradeTeacherDTO save(GradeTeacherDTO gradeTeacherDTO) {
        log.debug("Request to save GradeTeacher : {}", gradeTeacherDTO);
        GradeTeacher gradeTeacher = gradeTeacherMapper.toEntity(gradeTeacherDTO);
        gradeTeacher = gradeTeacherRepository.save(gradeTeacher);
        return gradeTeacherMapper.toDto(gradeTeacher);
    }

    /**
     * Get all the gradeTeachers.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GradeTeacherDTO> findAll() {
        log.debug("Request to get all GradeTeachers");
        return gradeTeacherRepository.findAll().stream()
            .map(gradeTeacherMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one gradeTeacher by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GradeTeacherDTO> findOne(Long id) {
        log.debug("Request to get GradeTeacher : {}", id);
        return gradeTeacherRepository.findById(id)
            .map(gradeTeacherMapper::toDto);
    }

    /**
     * Delete the gradeTeacher by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete GradeTeacher : {}", id);
        gradeTeacherRepository.deleteById(id);
    }
}
