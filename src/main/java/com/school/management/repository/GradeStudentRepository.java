package com.school.management.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.school.management.domain.GradeStudent;

/**
 * Spring Data repository for the GradeStudent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeStudentRepository extends JpaRepository<GradeStudent, Long> {

  @Query("select gradeStudent from GradeStudent gradeStudent where gradeStudent.user.login = ?#{principal.username}")
  List<GradeStudent> findByUserIsCurrentUser();

  @Query("select g from GradeStudent g where g.grade.id = (select t.grade.id from GradeTeacher t where t.user.login=?#{principal.username})")
  List<GradeStudent> findAllByTeacher();

  @Query("select g from GradeStudent g inner join g.grade where grade=?1")
  List<GradeStudent> findAllByGrade(int grade);
}
