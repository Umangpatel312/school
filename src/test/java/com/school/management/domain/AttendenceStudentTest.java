package com.school.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class AttendenceStudentTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendenceStudent.class);
        AttendenceStudent attendenceStudent1 = new AttendenceStudent();
        attendenceStudent1.setId(1L);
        AttendenceStudent attendenceStudent2 = new AttendenceStudent();
        attendenceStudent2.setId(attendenceStudent1.getId());
        assertThat(attendenceStudent1).isEqualTo(attendenceStudent2);
        attendenceStudent2.setId(2L);
        assertThat(attendenceStudent1).isNotEqualTo(attendenceStudent2);
        attendenceStudent1.setId(null);
        assertThat(attendenceStudent1).isNotEqualTo(attendenceStudent2);
    }
}
