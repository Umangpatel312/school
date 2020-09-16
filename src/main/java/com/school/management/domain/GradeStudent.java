package com.school.management.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A GradeStudent.
 */
@Entity
@Table(name = "grade_student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GradeStudent extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JsonIgnoreProperties(value = "gradeStudents", allowSetters = true)
  private User user;

  @ManyToOne
  @JsonIgnoreProperties(value = "gradeStudents", allowSetters = true)
  private Grade grade;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public GradeStudent user(User user) {
    this.user = user;
    return this;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Grade getGrade() {
    return grade;
  }

  public GradeStudent grade(Grade grade) {
    this.grade = grade;
    return this;
  }

  public void setGrade(Grade grade) {
    this.grade = grade;
  }
  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GradeStudent)) {
      return false;
    }
    return id != null && id.equals(((GradeStudent) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "GradeStudent{" + "id=" + getId() + "}";
  }
}
