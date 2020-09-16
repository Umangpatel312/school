package com.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.school.management.domain.GradeTeacher;

/**
 * Spring Data repository for the GradeTeacher entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GradeTeacherRepository extends JpaRepository<GradeTeacher, Long> {

  @Query("select gt from GradeTeacher gt where  gt.user.login=?#{principal.username}")
  public GradeTeacher findGradeByTeacher();
}
