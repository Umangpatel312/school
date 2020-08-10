package com.school.management.service;

import org.springframework.beans.factory.annotation.Autowired;
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
	public boolean findByEmail(SignUpDetail theSignUpDetail) {
		System.out.println("user input:"+theSignUpDetail);
		SignUpDetail signUpDetail=signUpDAO.findByEmail(theSignUpDetail.getEmail());
		System.out.println("database returned:"+signUpDetail);
		if(signUpDetail.getPassword().equals(theSignUpDetail.getPassword())){
			return true;
		}
		return false;
	}

}
