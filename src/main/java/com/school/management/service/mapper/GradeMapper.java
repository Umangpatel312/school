package com.school.management.service.mapper;


import com.school.management.domain.*;
import com.school.management.service.dto.GradeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Grade} and its DTO {@link GradeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GradeMapper extends EntityMapper<GradeDTO, Grade> {

    @Mapping(target = "attendences", ignore = true)
    @Mapping(target = "removeAttendence", ignore = true)
    @Mapping(target = "gradeStudents", ignore = true)
    @Mapping(target = "removeGradeStudent", ignore = true)
    @Mapping(target = "gradeTeacher", ignore = true)
    Grade toEntity(GradeDTO gradeDTO);

    default Grade fromId(Long id) {
        if (id == null) {
            return null;
        }
        Grade grade = new Grade();
        grade.setId(id);
        return grade;
    }
}
