package com.school.management.service.dto;

import java.time.Instant;
import java.io.Serializable;

/**
 * A DTO for the {@link com.school.management.domain.AttendenceDate} entity.
 */
public class AttendenceDateDTO implements Serializable {
    
    private Long id;

    private Instant date;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AttendenceDateDTO)) {
            return false;
        }

        return id != null && id.equals(((AttendenceDateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AttendenceDateDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
