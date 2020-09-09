package com.school.management.repository;

import com.school.management.domain.AttendenceDate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the AttendenceDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendenceDateRepository extends JpaRepository<AttendenceDate, Long> {
}
