package com.school.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class GradeTeacherTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GradeTeacher.class);
        GradeTeacher gradeTeacher1 = new GradeTeacher();
        gradeTeacher1.setId(1L);
        GradeTeacher gradeTeacher2 = new GradeTeacher();
        gradeTeacher2.setId(gradeTeacher1.getId());
        assertThat(gradeTeacher1).isEqualTo(gradeTeacher2);
        gradeTeacher2.setId(2L);
        assertThat(gradeTeacher1).isNotEqualTo(gradeTeacher2);
        gradeTeacher1.setId(null);
        assertThat(gradeTeacher1).isNotEqualTo(gradeTeacher2);
    }
}
