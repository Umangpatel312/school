package com.school.management.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {

  private int id;
  private String email;
  private String password;
  private int role;
  // Logger logger = LoggerFactory.getLogger(UserDTO.class);

  // public UserDTO() {}

  public UserDTO(String email, String password) {
    super();
    this.email = email;
    this.password = password;
  }



}
