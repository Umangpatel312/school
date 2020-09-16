package com.school.management.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class GradeStudentDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeStudentDTO.class);
        GradeStudentDTO gradeStudentDTO1 = new GradeStudentDTO();
        gradeStudentDTO1.setId(1L);
        GradeStudentDTO gradeStudentDTO2 = new GradeStudentDTO();
        assertThat(gradeStudentDTO1).isNotEqualTo(gradeStudentDTO2);
        gradeStudentDTO2.setId(gradeStudentDTO1.getId());
        assertThat(gradeStudentDTO1).isEqualTo(gradeStudentDTO2);
        gradeStudentDTO2.setId(2L);
        assertThat(gradeStudentDTO1).isNotEqualTo(gradeStudentDTO2);
        gradeStudentDTO1.setId(null);
        assertThat(gradeStudentDTO1).isNotEqualTo(gradeStudentDTO2);
    }
}
