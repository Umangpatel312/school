package com.school.management.service.mapper;


import com.school.management.domain.*;
import com.school.management.service.dto.AttendenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attendence} and its DTO {@link AttendenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, GradeMapper.class, AttendenceDateMapper.class})
public interface AttendenceMapper extends EntityMapper<AttendenceDTO, Attendence> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "grade.id", target = "gradeId")
    @Mapping(source = "grade.grade", target = "gradeGrade")
    @Mapping(source = "attendenceDate.id", target = "attendenceDateId")
    @Mapping(source = "attendenceDate.date", target = "attendenceDateDate")
    AttendenceDTO toDto(Attendence attendence);

    @Mapping(target = "attendenceStudents", ignore = true)
    @Mapping(target = "removeAttendenceStudent", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(source = "gradeId", target = "grade")
    @Mapping(source = "attendenceDateId", target = "attendenceDate")
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
