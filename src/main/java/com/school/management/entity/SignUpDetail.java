package com.school.management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="signin_details")
public class SignUpDetail {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
//	@Column(name="role")
//	private String role;
	
	public SignUpDetail() {
		
	}
	
	public SignUpDetail(String email, String password, String role) {
	
		this.email = email;
		this.password = password;
//		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SignUpDetail [email=" + email + ", password=" + password + "]";
	}

//	public String getRole() {
//		return role;
//	}
//
//	public void setRole(String role) {
//		this.role = role;
//	}

	
	
}
