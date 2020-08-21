package com.school.management.service;

import static java.util.Collections.emptyList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.school.management.dto.UserDTO;
import com.school.management.repository.UserRepository;
import com.school.management.restResource.UserNotFoundException;
import com.school.management.util.UserConverter;

@Service
public class UserLoginService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UserLoginService.class);

	private UserRepository userRepository;

	private UserConverter userConverter;

	@Autowired
	public UserLoginService(UserRepository userRepository, UserConverter userConverter) {
		this.userRepository = userRepository;
		this.userConverter = userConverter;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UserNotFoundException {

		logger.info("loadUserByUsername is invoked: " + email);

		com.school.management.entity.User tempUser = userRepository.findByEmail(email);

		logger.info("database returned:" + tempUser);

		if (tempUser == null) {
			logger.warn("database returned user: " + tempUser);
			throw new UserNotFoundException("Bad credentials");
		}

		return new User(tempUser.getEmail(), tempUser.getPassword(), emptyList());
	}

	public UserDTO save(UserDTO theUserDTO) {
		logger.info("sevice: save is invoked");
		com.school.management.entity.User theUser = userConverter.userDtoToEntity(theUserDTO);

		theUser = userRepository.save(theUser);

		return userConverter.userEntityToDTO(theUser);
	}

	public UserDTO update(String email, UserDTO theUserDTO) throws Exception {
		logger.info("===> update in service is processed:" + email);
		com.school.management.entity.User tempUser = userRepository.findByEmail(email);
		logger.info("===>database return: " + tempUser);
		if (tempUser == null) {
			throw new UserNotFoundException("Bad credentials");
		}
		tempUser.setEmail(theUserDTO.getEmail());
		tempUser.setPassword(theUserDTO.getPassword());
		com.school.management.entity.User user = userRepository.save(tempUser);
		return userConverter.userEntityToDTO(user);
	}

}
