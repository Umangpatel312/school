package com.school.management.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.school.management.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);

  User findById(int id);

  @Query("select uc.userId from User u inner join u.userAdded uc where u.email=?1 and uc.userId.role.id=?2")
  List<User> findByParticularRole(String username, int roleId);
}
