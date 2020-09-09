package com.school.management.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.school.management.domain.Grade} entity.
 */
public class GradeDTO implements Serializable {
    
    private Long id;

    private Integer grade;

    private String division;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GradeDTO)) {
            return false;
        }

        return id != null && id.equals(((GradeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GradeDTO{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            ", division='" + getDivision() + "'" +
            "}";
    }
}
