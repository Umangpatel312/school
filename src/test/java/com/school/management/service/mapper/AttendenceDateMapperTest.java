package com.school.management.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AttendenceDateMapperTest {

    private AttendenceDateMapper attendenceDateMapper;

    @BeforeEach
    public void setUp() {
        attendenceDateMapper = new AttendenceDateMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(attendenceDateMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(attendenceDateMapper.fromId(null)).isNull();
    }
}
