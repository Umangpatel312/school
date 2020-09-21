package com.school.management.repository;

import com.school.management.domain.Attendence;

import com.school.management.domain.AttendenceDate;
import com.school.management.domain.Grade;
import com.school.management.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Attendence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendenceRepository extends JpaRepository<Attendence, Long> {

    @Query("select attendence from Attendence attendence where attendence.user.login = ?#{principal.username}")
    List<Attendence> findByUserIsCurrentUser();

    Attendence findOneByUserIdAndAttendenceDateIdAndGradeId(long userId, long attendenceDateId, long gradeId);
}
