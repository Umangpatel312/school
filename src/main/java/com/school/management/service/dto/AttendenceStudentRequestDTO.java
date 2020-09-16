package com.school.management.service.dto;

public class AttendenceStudentRequestDTO extends AttendenceStudentDTO {


  private static final long serialVersionUID = 1L;

  private int gradeId;

  public AttendenceStudentRequestDTO() {}

  public int getGradeId() {
    return gradeId;
  }

  public void setGradeId(int gradeId) {
    this.gradeId = gradeId;
  }

  @Override
  public String toString() {
    return "AttendenceStudentRequestDTO [gradeId=" + gradeId + "]";
  }
}
