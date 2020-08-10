package com.school.management.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.management.entity.SignUpDetail;
import com.school.management.service.SignUpService;

@RestController
@RequestMapping("/principal")
public class PrincipalLogin {
	
	@Autowired
	private SignUpService signUpService;
	
	@PostMapping(value="/login",consumes = MediaType.APPLICATION_JSON_VALUE
			,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SignUpDetail> login(@RequestBody SignUpDetail theSignUpDetail) {
		System.out.println("login rest controller is invoked");
		boolean theLoginFlag=signUpService.findByEmail(theSignUpDetail);
		
		ResponseEntity<SignUpDetail> responseEntity=null;
		
		if(theLoginFlag==true) {
			responseEntity=new ResponseEntity<SignUpDetail>(theSignUpDetail, HttpStatus.ACCEPTED);
		}
		else {
			responseEntity=new ResponseEntity<SignUpDetail>(HttpStatus.BAD_REQUEST);
		}
		
		return responseEntity;
	}
}
