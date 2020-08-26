package com.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.school.management.entity.UserCreated;

public interface UserCreatedRepository extends JpaRepository<UserCreated, Integer> {

}
