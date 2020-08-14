package com.school.management.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserLoginService userLoginSevice;

	@PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
    public JwtResponse authenticate(@RequestBody User theUser) throws Exception {
        System.out.println("===>resources is processed");
		
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(theUser.getEmail(), theUser.getPassword())
            );

        System.out.println(theUser.getEmail());
        final String jwttoken= jwtUtil.generateToken(theUser.getEmail());
        System.out.println(jwttoken);
        System.out.println("end of the resources<====");
        
        return new JwtResponse(jwttoken);
	}
	
	@PutMapping(value="/update/{email}", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> updat(@PathVariable String email,@RequestBody User theUser) throws Exception {
		User tempUser=null;
		tempUser=userLoginSevice.update(email,theUser);
		
		return ResponseEntity.ok().body(tempUser); 
	}
	
	@PostMapping(value="/create", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createTeacher(@RequestBody User theUser) {
		
		userLoginSevice.save(theUser);
		
		return ResponseEntity.ok().body(theUser);
	}
	
	
	
	@GetMapping("/")
	public String welcome() {
		return "hello world";
	}
}
