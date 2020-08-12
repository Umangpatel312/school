package com.school.management.dao;


import com.school.management.entity.SignInDetail;

public interface SignInDAO {
	public SignInDetail findByEmail(String email);
	public void save(SignInDetail theSignInDetail);
}
