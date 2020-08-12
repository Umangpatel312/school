package com.school.management.service;

import static java.util.Collections.emptyList; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.management.dao.SignInDAO;
import com.school.management.entity.SignInDetail;

@Service
public class SignInServiceImpl implements SignInService {

	@Autowired
	private SignInDAO signInDAO;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//System.out.println("user input:"+theSignUpDetail);
		System.out.println("loadUserByUsername is invoked");
		SignInDetail signUpDetail=signInDAO.findByEmail(email);
		System.out.println("database returned:"+signUpDetail);
		if (signUpDetail == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(signUpDetail.getEmail(), signUpDetail.getPassword(), emptyList());
	}
	
	@Override
	@Transactional
	public void save(SignInDetail theSignInDetail) {
		System.out.println("sevice: save is invoked");
		signInDAO.save(theSignInDetail);
	}
}
