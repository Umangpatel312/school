package com.school.management.repository;

import java.time.Instant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.school.management.domain.AttendenceDate;

/**
 * Spring Data repository for the AttendenceDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendenceDateRepository extends JpaRepository<AttendenceDate, Long> {
  AttendenceDate findByDate(Instant date);
}
