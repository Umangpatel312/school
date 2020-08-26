package com.school.management.service;

import static java.util.Collections.emptyList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.school.management.dto.UserDTO;
import com.school.management.entity.UserCreated;
import com.school.management.repository.UserCreatedRepository;
import com.school.management.repository.UserRepository;
import com.school.management.restResource.UserNotFoundException;
import com.school.management.util.UserConverter;

@Service
public class UserLoginService implements UserDetailsService {

  final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

  private UserRepository userRepository;

  private UserCreatedRepository userCreatedRepository;
  private UserConverter userConverter;

  @Autowired
  public UserLoginService(UserRepository userRepository,
      UserCreatedRepository userCreatedRepository, UserConverter userConverter) {

    this.userRepository = userRepository;
    this.userCreatedRepository = userCreatedRepository;
    this.userConverter = userConverter;
  }

  @Override
  @Transactional
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


  public UserDTO save(UserDTO theUserDTO, String email) {
    logger.info("sevice: save is invoked");

    com.school.management.entity.User user = userRepository.findByEmail(email);

    com.school.management.entity.User theUser = userConverter.userDtoToEntity(theUserDTO);

    theUser = userRepository.save(theUser);

    UserCreated userCreated = new UserCreated();

    userCreated.setUserId(theUser);
    userCreated.setUserCreatedBy(user);
    userCreatedRepository.save(userCreated);

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

  public List<UserDTO> findAll() {
    List<com.school.management.entity.User> users = userRepository.findAll();
    List<UserDTO> usersDTO = userConverter.userEntityToDTO(users);
    return usersDTO;
  }

  public void findByAddedHim() {

  }

}
