package com.school.management.service.mapper;


import com.school.management.domain.*;
import com.school.management.service.dto.AttendenceStudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttendenceStudent} and its DTO {@link AttendenceStudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, AttendenceMapper.class})
public interface AttendenceStudentMapper extends EntityMapper<AttendenceStudentDTO, AttendenceStudent> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "attendence.id", target = "attendenceId")
    AttendenceStudentDTO toDto(AttendenceStudent attendenceStudent);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "attendenceId", target = "attendence")
    AttendenceStudent toEntity(AttendenceStudentDTO attendenceStudentDTO);

    default AttendenceStudent fromId(Long id) {
        if (id == null) {
            return null;
        }
        AttendenceStudent attendenceStudent = new AttendenceStudent();
        attendenceStudent.setId(id);
        return attendenceStudent;
    }
}
