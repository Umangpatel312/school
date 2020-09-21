package com.school.management.repository;

import com.school.management.service.dto.AttendenceDateDTO;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.school.management.domain.AttendenceDate;

/**
 * Spring Data repository for the AttendenceDate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AttendenceDateRepository extends JpaRepository<AttendenceDate, Long> {

    @Query("select distinct d from AttendenceDate d where d.date=?1")
    AttendenceDate findByDate(Instant formattedDate);
    @Query("select d from AttendenceDate d join d.attendences a join a.user u where u.login=?#{principal.username} "
        + "and d.date between ?1 and ?2 ")
    Page<AttendenceDate> findDateByTeacher(Instant from,Instant to, Pageable pageable);
}
