package com.school.management.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GradeMapperTest {

    private GradeMapper gradeMapper;

    @BeforeEach
    public void setUp() {
        gradeMapper = new GradeMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(gradeMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(gradeMapper.fromId(null)).isNull();
    }
}
