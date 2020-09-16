package com.school.management.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class GradeTeacherDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeTeacherDTO.class);
        GradeTeacherDTO gradeTeacherDTO1 = new GradeTeacherDTO();
        gradeTeacherDTO1.setId(1L);
        GradeTeacherDTO gradeTeacherDTO2 = new GradeTeacherDTO();
        assertThat(gradeTeacherDTO1).isNotEqualTo(gradeTeacherDTO2);
        gradeTeacherDTO2.setId(gradeTeacherDTO1.getId());
        assertThat(gradeTeacherDTO1).isEqualTo(gradeTeacherDTO2);
        gradeTeacherDTO2.setId(2L);
        assertThat(gradeTeacherDTO1).isNotEqualTo(gradeTeacherDTO2);
        gradeTeacherDTO1.setId(null);
        assertThat(gradeTeacherDTO1).isNotEqualTo(gradeTeacherDTO2);
    }
}
