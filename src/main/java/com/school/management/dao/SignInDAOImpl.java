package com.school.management.dao;

import java.util.List; 

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.school.management.entity.SignInDetail;

@Repository
public class SignInDAOImpl implements SignInDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public SignInDetail findByEmail(String email) {
		Session currentSession=entityManager.unwrap(Session.class);
		System.out.println("dao:"+email);
		Query<SignInDetail> query=currentSession.createQuery("from SignInDetail where email=:email");
		query.setParameter("email", email);
		List<SignInDetail> list=query.getResultList();
		System.out.println("List:"+list);
		if(list!=null)
			return list.get(0);
		return null;
	}
	
	@Override
	public void save(SignInDetail theSignInDetail) {
		Session currentSession=entityManager.unwrap(Session.class);
		System.out.println("password: "+theSignInDetail);
		currentSession.save(theSignInDetail);
		
	}

}
