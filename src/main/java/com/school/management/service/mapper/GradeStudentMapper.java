package com.school.management.service.mapper;


import com.school.management.domain.*;
import com.school.management.service.dto.GradeStudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GradeStudent} and its DTO {@link GradeStudentDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, GradeMapper.class})
public interface GradeStudentMapper extends EntityMapper<GradeStudentDTO, GradeStudent> {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    @Mapping(source = "grade.id", target = "gradeId")
    @Mapping(source = "grade.grade", target = "gradeGrade")
    GradeStudentDTO toDto(GradeStudent gradeStudent);

    @Mapping(source = "userId", target = "user")
    @Mapping(source = "gradeId", target = "grade")
    GradeStudent toEntity(GradeStudentDTO gradeStudentDTO);

    default GradeStudent fromId(Long id) {
        if (id == null) {
            return null;
        }
        GradeStudent gradeStudent = new GradeStudent();
        gradeStudent.setId(id);
        return gradeStudent;
    }
}
