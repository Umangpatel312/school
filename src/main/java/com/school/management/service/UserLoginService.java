package com.school.management.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.school.management.dto.UserDTO;
import com.school.management.entity.Role;
import com.school.management.entity.User;
import com.school.management.entity.UserCreated;
import com.school.management.repository.RoleRepository;
import com.school.management.repository.UserRepository;
import com.school.management.restResource.UserNotFoundException;
import com.school.management.util.UserConverter;

@Service
public class UserLoginService implements UserDetailsService {

  final Logger logger = LoggerFactory.getLogger(UserLoginService.class);

  private UserRepository userRepository;

  private RoleRepository roleRepository;
  private UserConverter userConverter;

  @Autowired
  public UserLoginService(UserRepository userRepository, RoleRepository roleRepository,
      UserConverter userConverter) {

    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.userConverter = userConverter;
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String email) throws UserNotFoundException {

    logger.info("loadUserByUsername is invoked: " + email);

    com.school.management.entity.User tempUser = userRepository.findByEmail(email);

    logger.info("database returned:" + tempUser);

    if (tempUser == null) {
      throw new UserNotFoundException("Bad credentials");
    }
    return new org.springframework.security.core.userdetails.User(tempUser.getEmail(),
        tempUser.getPassword(), getAuthority(tempUser));
  }

  private Set<SimpleGrantedAuthority> getAuthority(User user) {
    Set<SimpleGrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRole()));
    return authorities;
  }

  public UserDTO findByEmail(String theEmail) {
    return userConverter.userEntityToDTO(userRepository.findByEmail(theEmail));
  }

  @Transactional
  public UserDTO save(UserDTO theUserDTO, String email) {
    logger.info("sevice: save is invoked");

    User user = userRepository.findByEmail(email);

    User theUser = userConverter.userDtoToEntity(theUserDTO);

    Role role = roleRepository.findById(theUser.getRole().getId());
    logger.info(role.toString());
    if (role == null)
      throw new UserNotFoundException("role does not exist");
    logger.info(theUser.toString());

    role.addUser(theUser);

    UserCreated userCreated = new UserCreated();
    logger.info("service: new user added to db");
    userCreated.setUserId(theUser);
    userCreated.setUserCreatedBy(user);
    theUser.setUserCreated(userCreated);
    List<UserCreated> list = new ArrayList<UserCreated>();
    list.add(userCreated);
    theUser.setUserAdded(list);
    theUser = userRepository.save(theUser);

    // logger.info("service-create: " + theUser.getRole().toString());
    // logger.info("service: assing role-" + theUser.getRole());
    // userCreatedRepository.save(userCreated);

    return userConverter.userEntityToDTO(theUser);
  }

  public UserDTO update(int userId, UserDTO theUserDTO) throws Exception {
    logger.info("===> update in service is processed:" + userId);
    User tempUser = userRepository.findById(userId);
    logger.info("===>database return: " + tempUser);
    if (tempUser == null) {
      throw new UserNotFoundException("Bad credentials");
    }
    tempUser.setEmail(theUserDTO.getEmail());
    tempUser.setPassword(theUserDTO.getPassword());
    User user = userRepository.save(tempUser);
    return userConverter.userEntityToDTO(user);
  }

  public List<UserDTO> findByParticularRole(String username, int roleId) {

    User tempUser = userRepository.findByEmail(username);
    if (tempUser == null)
      throw new RuntimeException("bad user");
    List<User> listOfUser = userRepository.findByParticularRole(username, roleId);
    int size = listOfUser.size();
    logger.info("size" + size);
    logger.info(listOfUser.toString());
    listOfUser.forEach((user) -> logger.info("role:" + user.getRole().getRole()));

    List<UserDTO> listUserDTO = userConverter.userEntityToDTO(listOfUser);
    return listUserDTO;
  }

}
