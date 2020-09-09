package com.school.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Attendence.
 */
@Entity
@Table(name = "attendence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendences", allowSetters = true)
    private Grade grade;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendences", allowSetters = true)
    private AttendenceDate attendenceDate;

    @ManyToOne
    @JsonIgnoreProperties(value = "attendences", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Grade getGrade() {
        return grade;
    }

    public Attendence grade(Grade grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public AttendenceDate getAttendenceDate() {
        return attendenceDate;
    }

    public Attendence attendenceDate(AttendenceDate attendenceDate) {
        this.attendenceDate = attendenceDate;
        return this;
    }

    public void setAttendenceDate(AttendenceDate attendenceDate) {
        this.attendenceDate = attendenceDate;
    }

    public User getUser() {
        return user;
    }

    public Attendence user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attendence)) {
            return false;
        }
        return id != null && id.equals(((Attendence) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Attendence{" +
            "id=" + getId() +
            "}";
    }
}
