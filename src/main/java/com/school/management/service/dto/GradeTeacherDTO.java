package com.school.management.service.dto;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.school.management.domain.GradeTeacher} entity.
 */
public class GradeTeacherDTO implements Serializable {

  private Long id;

  private Instant date;


  private Long gradeId;

  private String gradeGrade;

  private Long userId;

  private String userLogin;

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

  public Instant getDate() {
    return date;
  }

  public void setDate(Instant date) {
    this.date = date;
  }

  public Long getGradeId() {
    return gradeId;
  }

  public void setGradeId(Long gradeId) {
    this.gradeId = gradeId;
  }

  public String getGradeGrade() {
    return gradeGrade;
  }

  public void setGradeGrade(String gradeGrade) {
    this.gradeGrade = gradeGrade;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof GradeTeacherDTO)) {
      return false;
    }

    return id != null && id.equals(((GradeTeacherDTO) o).id);
  }

  @Override
  public int hashCode() {
    return 31;
  }

  // prettier-ignore
  @Override
  public String toString() {
    return "GradeTeacherDTO{" + "id=" + getId() + ", date='" + getDate() + "'" + ", gradeId="
        + getGradeId() + ", gradeGrade='" + getGradeGrade() + "'" + ", userId=" + getUserId()
        + ", userLogin='" + getUserLogin() + "'" + "}";
  }
}
