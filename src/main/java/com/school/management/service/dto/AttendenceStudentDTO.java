package com.school.management.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.school.management.domain.AttendenceStudent} entity.
 */
public class AttendenceStudentDTO implements Serializable {

  private Long id;

  private String marked;

  private Long userId;

  private String userLogin;

  private Long attendenceId;

  private String createdBy;

  private Instant createdDate;

  private String lastModifiedBy;

  private Instant lastModifiedDate;

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  public String getLastModifiedBy() {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy) {
    this.lastModifiedBy = lastModifiedBy;
  }

  public Instant getLastModifiedDate() {
    return lastModifiedDate;
  }

  public void setLastModifiedDate(Instant lastModifiedDate) {
    this.lastModifiedDate = lastModifiedDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMarked() {
    return marked;
  }

  public void setMarked(String marked) {
    this.marked = marked;
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

  public Long getAttendenceId() {
    return attendenceId;
  }

  public void setAttendenceId(Long attendenceId) {
    this.attendenceId = attendenceId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof AttendenceStudentDTO)) {
      return false;
    }

    return id != null && id.equals(((AttendenceStudentDTO) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "AttendenceStudentDTO{" + "id=" + getId() + ", marked='" + getMarked() + "'"
        + ", userId=" + getUserId() + ", userLogin='" + getUserLogin() + "'" + ", attendenceId="
        + getAttendenceId() + "}";
  }
}
