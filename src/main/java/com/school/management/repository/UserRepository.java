package com.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.school.management.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);

}
