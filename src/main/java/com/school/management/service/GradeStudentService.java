package com.school.management.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.school.management.domain.GradeStudent;
import com.school.management.repository.GradeStudentRepository;
import com.school.management.repository.UserRepository;
import com.school.management.service.dto.GradeStudentDTO;
import com.school.management.service.mapper.GradeStudentMapper;

/**
 * Service Implementation for managing {@link GradeStudent}.
 */
@Service
@Transactional
public class GradeStudentService {

  private final Logger log = LoggerFactory.getLogger(GradeStudentService.class);

  private final GradeStudentRepository gradeStudentRepository;

  private final GradeStudentMapper gradeStudentMapper;
  @Autowired
  private UserRepository userRepository;

  public GradeStudentService(GradeStudentRepository gradeStudentRepository,
      GradeStudentMapper gradeStudentMapper) {
    this.gradeStudentRepository = gradeStudentRepository;
    this.gradeStudentMapper = gradeStudentMapper;
  }

  /**
   * Save a gradeStudent.
   *
   * @param gradeStudentDTO the entity to save.
   * @return the persisted entity.
   */
  public GradeStudentDTO save(GradeStudentDTO gradeStudentDTO) {
    log.debug("Request to save GradeStudent : {}", gradeStudentDTO);
    GradeStudent gradeStudent = gradeStudentMapper.toEntity(gradeStudentDTO);
    log.info("gradestudent service..{}", gradeStudent.getUser());
    log.info("gradestudent service..{}", gradeStudent.getUser().getId());
    log.info("gradestudent service..{}", userRepository.getOne(gradeStudent.getUser().getId()));
    gradeStudent = gradeStudentRepository.save(gradeStudent);
    return gradeStudentMapper.toDto(gradeStudent);
  }

  /**
   * Get all the gradeStudents.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<GradeStudentDTO> findAll() {
    log.debug("Request to get all GradeStudents");
    return gradeStudentRepository.findAll().stream().map(gradeStudentMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }


  /**
   * Get one gradeStudent by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<GradeStudentDTO> findOne(Long id) {
    log.debug("Request to get GradeStudent : {}", id);
    return gradeStudentRepository.findById(id).map(gradeStudentMapper::toDto);
  }

  /**
   * Delete the gradeStudent by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete GradeStudenta : {}", id);
    gradeStudentRepository.deleteById(id);
  }

  public List<GradeStudentDTO> findByTeacher() {

    return gradeStudentRepository.findAllByTeacher().stream().map(gradeStudentMapper::toDto)
        .collect(Collectors.toList());
  }

  public List<GradeStudentDTO> findByGrade(int grade) {
    return gradeStudentRepository.findAllByGrade(grade).stream().map(gradeStudentMapper::toDto)
        .collect(Collectors.toList());
  }
}
