package com.school.management.restResource;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.school.management.dto.UserDTO;
import com.school.management.entity.JwtResponse;
import com.school.management.service.UserLoginService;
import com.school.management.util.JwtUtil;

@RestController
public class UserLoginResource {

  Logger logger = LoggerFactory.getLogger(UserLoginResource.class);

  @Value("${jwt.token.expirationTime}")
  private long hour;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserLoginService userLoginService;


  @PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<JwtResponse> authenticate(@RequestBody UserDTO theUserDTO)
      throws Exception {
    logger.info("===>resources is processed");

    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(theUserDTO.getEmail(), theUserDTO.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    org.springframework.security.core.userdetails.User tempUser =
        (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
    logger.info("principal:" + tempUser);
    // final UserDTO userDTO = userLoginService.findByEmail(theUserDTO.getEmail());
    final String jwttoken = jwtUtil.generateToken(authentication);

    logger.info("end of the resources<====");

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Authorization", "Bearer " + jwttoken);

    JwtResponse jwtResponse = new JwtResponse(jwttoken);
    return new ResponseEntity<>(jwtResponse, httpHeaders, HttpStatus.OK);
  }

  // @PutMapping(value = "/update/{email:.+}", consumes = MediaType.APPLICATION_JSON_VALUE,
  @PutMapping(value = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<UserDTO> update(@PathVariable("id") int userId,
      @RequestBody UserDTO theUserDTO) throws Exception {
    logger.info("===>update method is processed:" + userId);
    UserDTO tempUserDTO = null;
    tempUserDTO = userLoginService.update(userId, theUserDTO);
    logger.info("===>ended update method: " + tempUserDTO);
    return ResponseEntity.ok(tempUserDTO);
  }

  @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = "application/json")
  public ResponseEntity<UserDTO> create(@RequestBody UserDTO theUserDTO,
      @RequestAttribute("username") String username) throws Exception {
    logger.info("token getting create method:" + username + " dto: " + theUserDTO);
    // throw new Exception();
    UserDTO userDTO = userLoginService.save(theUserDTO, username);
    logger.info("end-resourc-create: " + userDTO);
    return new ResponseEntity<UserDTO>(userDTO, HttpStatus.CREATED);
  }

  @GetMapping(value = "/getUserByParticularRole/{roleId}")
  public ResponseEntity<List<UserDTO>> getUsersByParticularRole(@PathVariable("roleId") int roleId,
      @RequestAttribute("username") String username) {

    logger.info("===>getUerByrole----");
    List<UserDTO> listOfUserDTO = userLoginService.findByParticularRole(username, roleId);
    return new ResponseEntity<List<UserDTO>>(listOfUserDTO, HttpStatus.OK);
  }

  @GetMapping("/")
  public String welcome() {
    return "hello world";
  }
}
