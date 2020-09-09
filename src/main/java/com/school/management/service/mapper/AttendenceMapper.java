package com.school.management.service.mapper;


import com.school.management.domain.*;
import com.school.management.service.dto.AttendenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendence} and its DTO {@link AttendenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {GradeMapper.class, AttendenceDateMapper.class, UserMapper.class})
public interface AttendenceMapper extends EntityMapper<AttendenceDTO, Attendence> {

    @Mapping(source = "grade.id", target = "gradeId")
    @Mapping(source = "attendenceDate.id", target = "attendenceDateId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    AttendenceDTO toDto(Attendence attendence);

    @Mapping(source = "gradeId", target = "grade")
    @Mapping(source = "attendenceDateId", target = "attendenceDate")
    @Mapping(source = "userId", target = "user")
    Attendence toEntity(AttendenceDTO attendenceDTO);

    default Attendence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Attendence attendence = new Attendence();
        attendence.setId(id);
        return attendence;
    }
}
