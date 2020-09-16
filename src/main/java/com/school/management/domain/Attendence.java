package com.school.management.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A Attendence.
 */
@Entity
@Table(name = "attendence")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Attendence extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "attendence", cascade = CascadeType.ALL)
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<AttendenceStudent> attendenceStudents = new HashSet<>();

  @ManyToOne
  @JsonIgnoreProperties(value = "attendences", allowSetters = true)
  private User user;

  @ManyToOne
  @JsonIgnoreProperties(value = "attendences", allowSetters = true)
  private Grade grade;

  @ManyToOne(
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
  @JsonIgnoreProperties(value = "attendences", allowSetters = true)
  private AttendenceDate attendenceDate;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<AttendenceStudent> getAttendenceStudents() {
    return attendenceStudents;
  }

  public Attendence attendenceStudents(Set<AttendenceStudent> attendenceStudents) {
    this.attendenceStudents = attendenceStudents;
    return this;
  }

  public Attendence addAttendenceStudent(AttendenceStudent attendenceStudent) {
    this.attendenceStudents.add(attendenceStudent);
    attendenceStudent.setAttendence(this);
    return this;
  }

  public Attendence removeAttendenceStudent(AttendenceStudent attendenceStudent) {
    this.attendenceStudents.remove(attendenceStudent);
    attendenceStudent.setAttendence(null);
    return this;
  }

  public void setAttendenceStudents(Set<AttendenceStudent> attendenceStudents) {
    this.attendenceStudents = attendenceStudents;
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
    return "Attendence{" + "id=" + getId() + "}";
  }
}
