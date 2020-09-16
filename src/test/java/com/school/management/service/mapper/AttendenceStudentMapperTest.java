package com.school.management.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttendenceStudentMapperTest {

    private AttendenceStudentMapper attendenceStudentMapper;

    @BeforeEach
    public void setUp() {
        attendenceStudentMapper = new AttendenceStudentMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attendenceStudentMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attendenceStudentMapper.fromId(null)).isNull();
    }
}
