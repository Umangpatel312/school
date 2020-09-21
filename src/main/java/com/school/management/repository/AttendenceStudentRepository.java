package com.school.management.repository;

import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.school.management.domain.AttendenceStudent;

/**
 * Spring Data repository for the AttendenceStudent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendenceStudentRepository extends JpaRepository<AttendenceStudent, Long> {

  @Query("select attendenceStudent from AttendenceStudent attendenceStudent where attendenceStudent.user.login = ?#{principal.username}")
  List<AttendenceStudent> findByUserIsCurrentUser();

  @Query("select s from AttendenceStudent s inner join s.attendence a "
      + "inner join a.attendenceDate d where d.date = ?1 and a.user.login=?#{principal.username}")
  List<AttendenceStudent> findByDate(Instant instant);
}
