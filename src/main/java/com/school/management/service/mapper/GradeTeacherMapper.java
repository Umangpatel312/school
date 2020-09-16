package com.school.management.service.mapper;


import com.school.management.domain.*;
import com.school.management.service.dto.GradeTeacherDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GradeTeacher} and its DTO {@link GradeTeacherDTO}.
 */
@Mapper(componentModel = "spring", uses = {GradeMapper.class, UserMapper.class})
public interface GradeTeacherMapper extends EntityMapper<GradeTeacherDTO, GradeTeacher> {

    @Mapping(source = "grade.id", target = "gradeId")
    @Mapping(source = "grade.grade", target = "gradeGrade")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    GradeTeacherDTO toDto(GradeTeacher gradeTeacher);

    @Mapping(source = "gradeId", target = "grade")
    @Mapping(source = "userId", target = "user")
    GradeTeacher toEntity(GradeTeacherDTO gradeTeacherDTO);

    default GradeTeacher fromId(Long id) {
        if (id == null) {
            return null;
        }
        GradeTeacher gradeTeacher = new GradeTeacher();
        gradeTeacher.setId(id);
        return gradeTeacher;
    }
}
