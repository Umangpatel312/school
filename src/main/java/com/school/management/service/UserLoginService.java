package com.school.management.service;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.school.management.repository.UserRepository;
import com.school.management.restController.UserNotFoundException;

@Service
public class UserLoginService implements UserDetailsService {

	
	private UserRepository userRepository;
	
	@Autowired
	public UserLoginService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UserNotFoundException {
		
		System.out.println("loadUserByUsername is invoked");
		
		com.school.management.entity.User tempUser=userRepository.findByEmail(email);
		
		System.out.println("database returned:"+tempUser);
		
		if (tempUser == null) {
            throw new UserNotFoundException("user not found");
        }
        
		return new User(tempUser.getEmail(), tempUser.getPassword(), emptyList());
	}
	
	
	public com.school.management.entity.User save(com.school.management.entity.User theUser) {
		System.out.println("sevice: save is invoked");
		return userRepository.save(theUser);
	}
	
	public com.school.management.entity.User update(String email,com.school.management.entity.User theUser) throws Exception{
		
		com.school.management.entity.User tempUser=userRepository.findByEmail(email);
		
		if(tempUser==null) {
			throw new UserNotFoundException("user not found");
		}
		
		tempUser.setEmail(theUser.getEmail());
		tempUser.setPassword(theUser.getPassword());
		
		return userRepository.save(tempUser);
	}
	
}
