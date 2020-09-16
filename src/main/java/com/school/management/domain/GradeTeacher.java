package com.school.management.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GradeTeacher.
 */
@Entity
@Table(name = "grade_teacher")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GradeTeacher extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date")
  private Instant date;

  @OneToOne
  @JoinColumn(unique = true)
  private Grade grade;

  @OneToOne
  @JoinColumn(unique = true)
  private User user;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Instant getDate() {
    return date;
  }

  public GradeTeacher date(Instant date) {
    this.date = date;
    return this;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public Grade getGrade() {
    return grade;
  }

  public GradeTeacher grade(Grade grade) {
    this.grade = grade;
    return this;
  }

  public void setGrade(Grade grade) {
    this.grade = grade;
  }

  public User getUser() {
    return user;
  }

  public GradeTeacher user(User user) {
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
    if (!(o instanceof GradeTeacher)) {
      return false;
    }
    return id != null && id.equals(((GradeTeacher) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "GradeTeacher{" + "id=" + getId() + ", date='" + getDate() + "'" + "}";
  }
}
