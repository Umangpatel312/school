package com.school.management.service;

import com.school.management.service.dto.AttendenceDateDTO;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    checkUserAlreadyTakenAttendence(attendence);
    List<AttendenceStudent> listOfAttendenceStudent = attendenceStudentDTO.stream()
        .map(attendenceStudentMapper::toEntity).collect(Collectors.toList());
    for (AttendenceStudent attendenceStudent : listOfAttendenceStudent) {
      attendence.addAttendenceStudent(attendenceStudent);
    }
    return attendenceRepository.save(attendence).getAttendenceStudents().stream()
        .map(attendenceStudentMapper::toDto).collect(Collectors.toList());
  }

  private void checkUserAlreadyTakenAttendence(@NotNull Attendence newAttendence) {
    Attendence tempAttendence = attendenceRepository.findOneByUserIdAndAttendenceDateIdAndGradeId(
        newAttendence.getUser().getId(), newAttendence.getAttendenceDate().getId(),
        newAttendence.getGrade().getId());
    if (tempAttendence!=null ) {
      throw new AttendenceAlreadyExist();
    }
  }

  private AttendenceDate getDate() {
    Instant instant = Instant.now();
      instant = instant.truncatedTo( ChronoUnit.DAYS );
      AttendenceDate attendenceDate = attendenceDateRepository.findByDate(instant);
    log.info("zone:===={}", instant);
    if (attendenceDate == null) {
      attendenceDate = new AttendenceDate();
      attendenceDate.setDate(instant);
      attendenceDate = attendenceDateRepository.save(attendenceDate);
    }
    log.debug("date:{}", attendenceDate);
    return attendenceDate;
  }

  public List<AttendenceStudentDTO> findByDate(@NotNull LocalDate localDate) {
    log.info("AttendenceStudentDTO---date service:{}", localDate);
      Instant instant = Instant.now().truncatedTo( ChronoUnit.DAYS );
      return attendenceStudentRepository.findByDate(instant).stream()
        .map(attendenceStudentMapper::toDto).collect(Collectors.toList());
  }


    public Page<AttendenceDateDTO> findDateByTeacher(@NotNull LocalDate fromDate,@NotNull LocalDate toDate,
        Pageable pageable) {
        Instant from = fromDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        Instant to = toDate.atStartOfDay().toInstant(ZoneOffset.UTC);
        log.info("from:---{}\n To:---{}", from,to);
      Page<AttendenceDate> page= attendenceDateRepository.findDateByTeacher(from,to,pageable);
      return  page.map(AttendenceDateDTO::new);
  }
}
