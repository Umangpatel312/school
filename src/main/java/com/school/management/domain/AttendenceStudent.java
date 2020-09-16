package com.school.management.domain;

import java.io.Serializable;
import javax.persistence.Column;
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
 * A AttendenceStudent.
 */
@Entity
@Table(name = "attendence_student")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttendenceStudent extends AbstractAuditingEntity implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "marked")
  private String marked;

  @ManyToOne
  @JsonIgnoreProperties(value = "attendenceStudents", allowSetters = true)
  private User user;

  @ManyToOne
  @JsonIgnoreProperties(value = "attendenceStudents", allowSetters = true)
  private Attendence attendence;

  // jhipster-needle-entity-add-field - JHipster will add fields here
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMarked() {
    return marked;
  }

  public AttendenceStudent marked(String marked) {
    this.marked = marked;
    return this;
  }

  public void setMarked(String marked) {
    this.marked = marked;
  }

  public User getUser() {
    return user;
  }

  public AttendenceStudent user(User user) {
    this.user = user;
    return this;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Attendence getAttendence() {
    return attendence;
  }

  public AttendenceStudent attendence(Attendence attendence) {
    this.attendence = attendence;
    return this;
  }

  public void setAttendence(Attendence attendence) {
    this.attendence = attendence;
  }
  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AttendenceStudent)) {
      return false;
    }
    return id != null && id.equals(((AttendenceStudent) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "AttendenceStudent{" + "id=" + getId() + ", marked='" + getMarked() + "'" + "}";
  }
}
