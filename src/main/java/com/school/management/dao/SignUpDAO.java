package com.school.management.dao;

import com.school.management.entity.SignUpDetail;

public interface SignUpDAO {
	public SignUpDetail findByEmail(String email);
}
