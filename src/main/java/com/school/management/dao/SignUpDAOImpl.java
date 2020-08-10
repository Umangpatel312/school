package com.school.management.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.school.management.entity.SignUpDetail;

@Repository
public class SignUpDAOImpl implements SignUpDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	@Transactional
	public SignUpDetail findByEmail(String email) {
		Session currentSession=entityManager.unwrap(Session.class);
		System.out.println(email);
		Query<SignUpDetail> query=currentSession.createQuery("from SignUpDetail where email=:email");
		query.setParameter("email", email);
		List<SignUpDetail> list=query.getResultList();
		if(list!=null)
			return list.get(0);
		return null;
	}

}
