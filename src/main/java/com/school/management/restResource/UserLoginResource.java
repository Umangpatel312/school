package com.school.management.restResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.entity.JwtResponse;
import com.school.management.entity.User;
import com.school.management.service.UserLoginService;
import com.school.management.util.JwtUtil;

@RestController
public class UserLoginResource {

	Logger logger= LoggerFactory.getLogger(UserLoginResource.class);
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserLoginService userLoginSevice;

	@PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponse authenticate(@RequestBody User theUser) throws Exception {
       logger.info("===>resources is processed");
		
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(theUser.getEmail(), theUser.getPassword())
            );

        final String jwttoken= jwtUtil.generateToken(theUser.getEmail());
        logger.info(jwttoken);
        logger.info("end of the resources<====");
        
        return new JwtResponse(jwttoken);
	}
	
	@PutMapping(value="/update/{email}", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> update(@PathVariable String email,@RequestBody User theUser) throws Exception {
		User tempUser=null;
		tempUser=userLoginSevice.update(email,theUser);
		
		return ResponseEntity.ok().body(tempUser); 
	}
	
	@PostMapping(value="/create", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> create(@RequestBody User theUser) {

		return ResponseEntity.ok().body(userLoginSevice.save(theUser));
	}
	
	
	
	@GetMapping("/")
	public String welcome() {
		return "hello world";
	}
}
