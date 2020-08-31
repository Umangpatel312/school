package com.school.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.school.management.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
  public Role findById(int id);
}
