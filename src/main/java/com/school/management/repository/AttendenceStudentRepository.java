package com.school.management.repository;

import com.school.management.domain.AttendenceStudent;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the AttendenceStudent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendenceStudentRepository extends JpaRepository<AttendenceStudent, Long> {

    @Query("select attendenceStudent from AttendenceStudent attendenceStudent where attendenceStudent.user.login = ?#{principal.username}")
    List<AttendenceStudent> findByUserIsCurrentUser();
}
