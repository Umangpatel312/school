package com.school.management.domain;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AttendenceDate.
 */
@Entity
@Table(name = "attendence_date")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AttendenceDate implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date")
  private Instant date;

  @OneToMany(mappedBy = "attendenceDate")
  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
  private Set<Attendence> attendences = new HashSet<>();

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

  public AttendenceDate date(Instant date) {
    this.date = date;
    return this;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public Set<Attendence> getAttendences() {
    return attendences;
  }

  public AttendenceDate attendences(Set<Attendence> attendences) {
    this.attendences = attendences;
    return this;
  }

  public AttendenceDate addAttendence(Attendence attendence) {
    this.attendences.add(attendence);
    attendence.setAttendenceDate(this);
    return this;
  }

  public AttendenceDate removeAttendence(Attendence attendence) {
    this.attendences.remove(attendence);
    attendence.setAttendenceDate(null);
    return this;
  }

  public void setAttendences(Set<Attendence> attendences) {
    this.attendences = attendences;
  }
  // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AttendenceDate)) {
      return false;
    }
    return id != null && id.equals(((AttendenceDate) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "AttendenceDate{" + "id=" + getId() + ", date='" + getDate() + "'" + "}";
  }
}
