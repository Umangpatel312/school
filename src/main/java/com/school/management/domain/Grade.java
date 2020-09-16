package com.school.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Grade.
 */
@Entity
@Table(name = "grade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade")
    private Integer grade;

    @OneToMany(mappedBy = "grade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Attendence> attendences = new HashSet<>();

    @OneToMany(mappedBy = "grade")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<GradeStudent> gradeStudents = new HashSet<>();

    @OneToOne(mappedBy = "grade")
    @JsonIgnore
    private GradeTeacher gradeTeacher;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGrade() {
        return grade;
    }

    public Grade grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Set<Attendence> getAttendences() {
        return attendences;
    }

    public Grade attendences(Set<Attendence> attendences) {
        this.attendences = attendences;
        return this;
    }

    public Grade addAttendence(Attendence attendence) {
        this.attendences.add(attendence);
        attendence.setGrade(this);
        return this;
    }

    public Grade removeAttendence(Attendence attendence) {
        this.attendences.remove(attendence);
        attendence.setGrade(null);
        return this;
    }

    public void setAttendences(Set<Attendence> attendences) {
        this.attendences = attendences;
    }

    public Set<GradeStudent> getGradeStudents() {
        return gradeStudents;
    }

    public Grade gradeStudents(Set<GradeStudent> gradeStudents) {
        this.gradeStudents = gradeStudents;
        return this;
    }

    public Grade addGradeStudent(GradeStudent gradeStudent) {
        this.gradeStudents.add(gradeStudent);
        gradeStudent.setGrade(this);
        return this;
    }

    public Grade removeGradeStudent(GradeStudent gradeStudent) {
        this.gradeStudents.remove(gradeStudent);
        gradeStudent.setGrade(null);
        return this;
    }

    public void setGradeStudents(Set<GradeStudent> gradeStudents) {
        this.gradeStudents = gradeStudents;
    }

    public GradeTeacher getGradeTeacher() {
        return gradeTeacher;
    }

    public Grade gradeTeacher(GradeTeacher gradeTeacher) {
        this.gradeTeacher = gradeTeacher;
        return this;
    }

    public void setGradeTeacher(GradeTeacher gradeTeacher) {
        this.gradeTeacher = gradeTeacher;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grade)) {
            return false;
        }
        return id != null && id.equals(((Grade) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grade{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            "}";
    }
}
