package com.school.management.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.entity.SignUpDetail;
import com.school.management.util.JwtUtil;

@RestController
@RequestMapping("/principal")
public class PrincipalLogin {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
    public String generateToken(@RequestBody SignUpDetail signUpDetail) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signUpDetail.getEmail(), signUpDetail.getPassword())
            );
        } catch (Exception ex) {
            throw new Exception("inavalid username/password");
        }
        return jwtUtil.generateToken(signUpDetail.getEmail());
    }
	
	@GetMapping("/")
	public String welcome() {
		return "hello world";
	}
	

}
