package com.school.management.service.mapper;


import com.school.management.domain.*;
import com.school.management.service.dto.AttendenceDateDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttendenceDate} and its DTO {@link AttendenceDateDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttendenceDateMapper extends EntityMapper<AttendenceDateDTO, AttendenceDate> {



    default AttendenceDate fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttendenceDate attendenceDate = new AttendenceDate();
        attendenceDate.setId(id);
        return attendenceDate;
    }
}
