package com.school.management.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.school.management.domain.Attendence} entity.
 */
public class AttendenceDTO implements Serializable {
    
    private Long id;


    private Long gradeId;

    private Long attendenceDateId;

    private Long userId;

    private String userLogin;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGradeId() {
        return gradeId;
    }

    public void setGradeId(Long gradeId) {
        this.gradeId = gradeId;
    }

    public Long getAttendenceDateId() {
        return attendenceDateId;
    }

    public void setAttendenceDateId(Long attendenceDateId) {
        this.attendenceDateId = attendenceDateId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendenceDTO)) {
            return false;
        }

        return id != null && id.equals(((AttendenceDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendenceDTO{" +
            "id=" + getId() +
            ", gradeId=" + getGradeId() +
            ", attendenceDateId=" + getAttendenceDateId() +
            ", userId=" + getUserId() +
            ", userLogin='" + getUserLogin() + "'" +
            "}";
    }
}
