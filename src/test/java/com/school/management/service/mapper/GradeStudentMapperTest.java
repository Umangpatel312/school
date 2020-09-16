package com.school.management.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GradeStudentMapperTest {

    private GradeStudentMapper gradeStudentMapper;

    @BeforeEach
    public void setUp() {
        gradeStudentMapper = new GradeStudentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gradeStudentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gradeStudentMapper.fromId(null)).isNull();
    }
}
