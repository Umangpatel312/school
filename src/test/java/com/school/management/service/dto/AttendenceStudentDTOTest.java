package com.school.management.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class AttendenceStudentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendenceStudentDTO.class);
        AttendenceStudentDTO attendenceStudentDTO1 = new AttendenceStudentDTO();
        attendenceStudentDTO1.setId(1L);
        AttendenceStudentDTO attendenceStudentDTO2 = new AttendenceStudentDTO();
        assertThat(attendenceStudentDTO1).isNotEqualTo(attendenceStudentDTO2);
        attendenceStudentDTO2.setId(attendenceStudentDTO1.getId());
        assertThat(attendenceStudentDTO1).isEqualTo(attendenceStudentDTO2);
        attendenceStudentDTO2.setId(2L);
        assertThat(attendenceStudentDTO1).isNotEqualTo(attendenceStudentDTO2);
        attendenceStudentDTO1.setId(null);
        assertThat(attendenceStudentDTO1).isNotEqualTo(attendenceStudentDTO2);
    }
}
