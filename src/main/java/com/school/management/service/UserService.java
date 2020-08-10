package com.school.management.service;

import com.school.management.dto.UserDTO;

public class UserService {

  public UserDTO getUser(Long id) {
    UserDTO userDTO = new UserDTO("John", "Doe");
    System.out.println(userDTO);
    return userDTO;
  }
}
