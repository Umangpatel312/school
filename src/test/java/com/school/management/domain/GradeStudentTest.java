package com.school.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class GradeStudentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeStudent.class);
        GradeStudent gradeStudent1 = new GradeStudent();
        gradeStudent1.setId(1L);
        GradeStudent gradeStudent2 = new GradeStudent();
        gradeStudent2.setId(gradeStudent1.getId());
        assertThat(gradeStudent1).isEqualTo(gradeStudent2);
        gradeStudent2.setId(2L);
        assertThat(gradeStudent1).isNotEqualTo(gradeStudent2);
        gradeStudent1.setId(null);
        assertThat(gradeStudent1).isNotEqualTo(gradeStudent2);
    }
}
