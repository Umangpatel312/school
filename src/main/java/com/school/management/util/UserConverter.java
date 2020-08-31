package com.school.management.util;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.school.management.dto.UserDTO;
import com.school.management.entity.User;

@Component
public class UserConverter {

  private ModelMapper modelMapper;

  @Autowired
  public UserConverter(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;

    this.modelMapper.addMappings(userMapping);
    this.modelMapper.addMappings(userDTOMapping);
  }

  public UserDTO userEntityToDTO(User theUser) {
    return modelMapper.map(theUser, UserDTO.class);
  }

  public List<UserDTO> userEntityToDTO(List<User> theUser) {
    return theUser.stream().map(user -> userEntityToDTO(user)).collect(Collectors.toList());
  }

  public User userDtoToEntity(UserDTO theUserDTO) {
    return modelMapper.map(theUserDTO, User.class);
  }

  public List<User> userDtoToEntity(List<UserDTO> theUserDTO) {
    return theUserDTO.stream().map(userDTO -> userDtoToEntity(userDTO))
        .collect(Collectors.toList());
  }

  PropertyMap<UserDTO, User> userMapping = new PropertyMap<UserDTO, User>() {
    protected void configure() {
      map().getRole().setId(source.getRole());
      map().getRole().setRole(null);
    }
  };
  PropertyMap<User, UserDTO> userDTOMapping = new PropertyMap<User, UserDTO>() {
    protected void configure() {
      map().setRole(source.getRole().getId());
    }
  };
}
