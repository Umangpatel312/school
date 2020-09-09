package com.school.management.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class AttendenceDateDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendenceDateDTO.class);
        AttendenceDateDTO attendenceDateDTO1 = new AttendenceDateDTO();
        attendenceDateDTO1.setId(1L);
        AttendenceDateDTO attendenceDateDTO2 = new AttendenceDateDTO();
        assertThat(attendenceDateDTO1).isNotEqualTo(attendenceDateDTO2);
        attendenceDateDTO2.setId(attendenceDateDTO1.getId());
        assertThat(attendenceDateDTO1).isEqualTo(attendenceDateDTO2);
        attendenceDateDTO2.setId(2L);
        assertThat(attendenceDateDTO1).isNotEqualTo(attendenceDateDTO2);
        attendenceDateDTO1.setId(null);
        assertThat(attendenceDateDTO1).isNotEqualTo(attendenceDateDTO2);
    }
}
