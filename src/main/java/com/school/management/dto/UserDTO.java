package com.school.management.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDTO {

  private int id;
  private String email;
  private String password;
  private String role;

  // public UserDTO() {}

  public UserDTO(String email, String password) {
    super();
    this.email = email;
    this.password = password;
  }


}
