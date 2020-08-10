package com.school.management.service;

import com.school.management.entity.SignUpDetail;

public interface SignUpService {
	public boolean findByEmail(SignUpDetail theSignUpDetail); 
}
