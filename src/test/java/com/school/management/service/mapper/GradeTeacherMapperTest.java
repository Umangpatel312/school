package com.school.management.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GradeTeacherMapperTest {

    private GradeTeacherMapper gradeTeacherMapper;

    @BeforeEach
    public void setUp() {
        gradeTeacherMapper = new GradeTeacherMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gradeTeacherMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gradeTeacherMapper.fromId(null)).isNull();
    }
}
