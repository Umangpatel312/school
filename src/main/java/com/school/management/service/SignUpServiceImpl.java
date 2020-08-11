package com.school.management.service;

import static java.util.Collections.emptyList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.school.management.dao.SignUpDAO;
import com.school.management.entity.SignUpDetail;

@Service
public class SignUpServiceImpl implements SignUpService {

	@Autowired
	private SignUpDAO signUpDAO;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//System.out.println("user input:"+theSignUpDetail);
		System.out.println("loadUserByUsername is invoked");
		SignUpDetail signUpDetail=signUpDAO.findByEmail(email);
		//System.out.println("database returned:"+signUpDetail);
		if (signUpDetail == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(signUpDetail.getEmail(), signUpDetail.getPassword(), emptyList());
	}
}
