package com.school.management.repository;

import com.school.management.domain.Attendence;

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
}
