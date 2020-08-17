package com.school.management.service;

import static java.util.Collections.emptyList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.school.management.repository.UserRepository;
import com.school.management.restResource.UserNotFoundException;

@Service
public class UserLoginService implements UserDetailsService {

	Logger logger= LoggerFactory.getLogger(UserLoginService.class);
	
	private UserRepository userRepository;
	
	@Autowired
	public UserLoginService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
		
		logger.info("loadUserByUsername is invoked: "+email);
		
		com.school.management.entity.User tempUser=userRepository.findByEmail(email);
		
		logger.info("database returned:"+tempUser);
		
		if (tempUser == null) {
           logger.warn("database returned user: "+tempUser);
			throw new UserNotFoundException("Bad credentials");
        }
        
		return new User(tempUser.getEmail(), tempUser.getPassword(), emptyList());
	}
	
	
	public com.school.management.entity.User save(com.school.management.entity.User theUser) {
		logger.info("sevice: save is invoked");
		return userRepository.save(theUser);
	}
	
	public com.school.management.entity.User update(String email,com.school.management.entity.User theUser) throws Exception{
		
		com.school.management.entity.User tempUser=userRepository.findByEmail(email);
		
		if(tempUser==null) {
			throw new UserNotFoundException("Bad credentials");
		}
		
		tempUser.setEmail(theUser.getEmail());
		tempUser.setPassword(theUser.getPassword());
		
		return userRepository.save(tempUser);
	}
	
}
