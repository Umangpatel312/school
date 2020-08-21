package com.school.management.util;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
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
	}
	
	public UserDTO userEntityToDTO(User theUser) {
		return modelMapper.map(theUser,UserDTO.class);
	}
	
	public List<UserDTO> userEntityToDTO(List<User> theUser) {
		return theUser.
				stream().
				map(user -> userEntityToDTO(user)).
				collect(Collectors.toList());
	}
	
	public User userDtoToEntity(UserDTO theUserDTO) {
		return modelMapper.map(theUserDTO,User.class);
	}
	
	public List<User> userDtoToEntity(List<UserDTO> theUserDTO) {
		return theUserDTO.
				stream().
				map(userDTO -> userDtoToEntity(userDTO)).
				collect(Collectors.toList());
	}
}
