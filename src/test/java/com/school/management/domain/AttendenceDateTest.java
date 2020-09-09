package com.school.management.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.school.management.web.rest.TestUtil;

public class AttendenceDateTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttendenceDate.class);
        AttendenceDate attendenceDate1 = new AttendenceDate();
        attendenceDate1.setId(1L);
        AttendenceDate attendenceDate2 = new AttendenceDate();
        attendenceDate2.setId(attendenceDate1.getId());
        assertThat(attendenceDate1).isEqualTo(attendenceDate2);
        attendenceDate2.setId(2L);
        assertThat(attendenceDate1).isNotEqualTo(attendenceDate2);
        attendenceDate1.setId(null);
        assertThat(attendenceDate1).isNotEqualTo(attendenceDate2);
    }
}
