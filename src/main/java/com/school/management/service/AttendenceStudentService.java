package com.school.management.service;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.school.management.domain.Attendence;
import com.school.management.domain.AttendenceDate;
import com.school.management.domain.AttendenceStudent;
import com.school.management.domain.GradeTeacher;
import com.school.management.repository.AttendenceDateRepository;
import com.school.management.repository.AttendenceRepository;
import com.school.management.repository.AttendenceStudentRepository;
import com.school.management.repository.GradeTeacherRepository;
import com.school.management.service.dto.AttendenceStudentDTO;
import com.school.management.service.mapper.AttendenceStudentMapper;

/**
 * Service Implementation for managing {@link AttendenceStudent}.
 */
@Service
@Transactional
public class AttendenceStudentService {

  private final Logger log = LoggerFactory.getLogger(AttendenceStudentService.class);

  private final AttendenceStudentRepository attendenceStudentRepository;

  private final AttendenceRepository attendenceRepository;
  private final AttendenceStudentMapper attendenceStudentMapper;
  private final AttendenceDateRepository attendenceDateRepository;
  private final GradeTeacherRepository gradeTeacherRepository;

  public AttendenceStudentService(AttendenceStudentRepository attendenceStudentRepository,
      AttendenceStudentMapper attendenceStudentMapper, AttendenceRepository attendenceRepository,
      AttendenceDateRepository attendenceDateRepository,
      GradeTeacherRepository gradeTeacherRepository) {
    this.attendenceStudentRepository = attendenceStudentRepository;
    this.attendenceStudentMapper = attendenceStudentMapper;
    this.attendenceRepository = attendenceRepository;
    this.attendenceDateRepository = attendenceDateRepository;
    this.gradeTeacherRepository = gradeTeacherRepository;
  }

  /**
   * Save a attendenceStudent.
   *
   * @param attendenceStudentDTO the entity to save.
   * @return the persisted entity.
   */
  public AttendenceStudentDTO save(AttendenceStudentDTO attendenceStudentDTO) {
    log.debug("Request to save AttendenceStudent : {}", attendenceStudentDTO);
    AttendenceStudent attendenceStudent = attendenceStudentMapper.toEntity(attendenceStudentDTO);
    attendenceStudent = attendenceStudentRepository.save(attendenceStudent);
    return attendenceStudentMapper.toDto(attendenceStudent);
  }

  /**
   * Get all the attendenceStudents.
   *
   * @return the list of entities.
   */
  @Transactional(readOnly = true)
  public List<AttendenceStudentDTO> findAll() {
    log.debug("Request to get all AttendenceStudents");
    return attendenceStudentRepository.findAll().stream().map(attendenceStudentMapper::toDto)
        .collect(Collectors.toCollection(LinkedList::new));
  }


  /**
   * Get one attendenceStudent by id.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  @Transactional(readOnly = true)
  public Optional<AttendenceStudentDTO> findOne(Long id) {
    log.debug("Request to get AttendenceStudent : {}", id);
    return attendenceStudentRepository.findById(id).map(attendenceStudentMapper::toDto);
  }

  /**
   * Delete the attendenceStudent by id.
   *
   * @param id the id of the entity.
   */
  public void delete(Long id) {
    log.debug("Request to delete AttendenceStudent : {}", id);
    attendenceStudentRepository.deleteById(id);
  }

  public List<AttendenceStudentDTO> createAttendence(
      List<AttendenceStudentDTO> attendenceStudentDTO) {
    AttendenceDate attendenceDate = getDate();
    Attendence attendence = new Attendence();
    attendence.setAttendenceDate(attendenceDate);
    GradeTeacher gradeTeacher = gradeTeacherRepository.findGradeByTeacher();
    attendence.setGrade(gradeTeacher.getGrade());
    attendence.setUser(gradeTeacher.getUser());
    // AttendenceStudent attendenceStudent = null;
    List<AttendenceStudent> listOfAttendenceStudent = attendenceStudentDTO.stream()
        .map(attendenceStudentMapper::toEntity).collect(Collectors.toList());
    for (AttendenceStudent attendenceStudent : listOfAttendenceStudent) {
      log.debug("att:{}", attendenceStudent.getAttendence());
      log.debug("att:{}", attendenceStudent);
      attendence.addAttendenceStudent(attendenceStudent);
    }
    List<AttendenceStudentDTO> listOfResponse =
        attendenceRepository.save(attendence).getAttendenceStudents().stream()
            .map(attendenceStudentMapper::toDto).collect(Collectors.toList());
    return listOfResponse;
  }

  private AttendenceDate getDate() {
    Date date = new Date(Calendar.getInstance().getTime().getTime());
    log.info("date:{}", date);
    LocalDate dateLocal = date.toLocalDate();
    Instant instant = dateLocal.atStartOfDay(ZoneOffset.UTC).toInstant();
    AttendenceDate attendenceDate = attendenceDateRepository.findByDate(instant);
    log.debug("" + attendenceDate);
    if (attendenceDate == null) {
      attendenceDate = new AttendenceDate();
      attendenceDate.setDate(instant);
    }
    log.debug("date:{}", attendenceDate);
    return attendenceDate;
  }
}
