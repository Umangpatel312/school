package com.school.management.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.school.management.entity.SignInDetail;

public interface SignInService extends UserDetailsService {
	 public void save(SignInDetail theSignInDetail);
}
