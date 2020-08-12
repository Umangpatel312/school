package com.school.management.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.entity.JwtResponse;
import com.school.management.entity.SignInDetail;
import com.school.management.service.SignInService;
import com.school.management.util.JwtUtil;

@RestController
@RequestMapping("/generic")
public class PrincipalLogin {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SignInService signInService;

	@PostMapping(value = "/authenticate", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody SignInDetail signInDetail) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInDetail.getEmail(), signInDetail.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        final String jwttoken= jwtUtil.generateToken(signInDetail.getEmail());
        return ResponseEntity.ok(new JwtResponse(jwttoken));
	}
	
	@PutMapping(value="/update", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SignInDetail> updatePrincipal(@RequestBody SignInDetail theSignInDetail) {
		
		signInService.save(theSignInDetail);
		
		return ResponseEntity.ok().body(theSignInDetail); 
	}
	
	@PostMapping(value="/createTeacher", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SignInDetail> createTeacher(@RequestBody SignInDetail theSignInDetail) {
		
		System.out.println("createTeacher is invoked");
		signInService.save(theSignInDetail);
		
		return ResponseEntity.ok().body(theSignInDetail);
	}
	
	@PostMapping(value="/createStudent", consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SignInDetail> createStudent(@RequestBody SignInDetail theSignInDetail) {
		signInService.save(theSignInDetail);
		
		return ResponseEntity.ok().body(theSignInDetail);
	} 
	
	@GetMapping("/")
	public String welcome() {
		return "hello world";
	}
}
