package com.school.management.service;

import com.school.management.dto.UserDTO;

public class UserService {

  public UserDTO getUser(Long id) {
    return new UserDTO("John", "Doe");
  }
}
