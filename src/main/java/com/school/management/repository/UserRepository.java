package com.school.management.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.school.management.domain.User;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  String USERS_BY_LOGIN_CACHE = "usersByLogin";

  String USERS_BY_EMAIL_CACHE = "usersByEmail";

  Optional<User> findOneByActivationKey(String activationKey);

  List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(
      Instant dateTime);

  Optional<User> findOneByResetKey(String resetKey);

  Optional<User> findOneByEmailIgnoreCase(String email);

  Optional<User> findOneByLogin(String login);

  @EntityGraph(attributePaths = "authorities")
  @Cacheable(cacheNames = USERS_BY_LOGIN_CACHE)
  Optional<User> findOneWithAuthoritiesByLogin(String login);

  @EntityGraph(attributePaths = "authorities")
  @Cacheable(cacheNames = USERS_BY_EMAIL_CACHE)
  Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

  Page<User> findAllByLoginNot(Pageable pageable, String login);

  @Query("select u from User u inner join u.authorities a where u.createdBy=?1 "
      + "and a.name=?2 or u.createdBy in (select u.login from User u "
      + "inner join u.authorities a where u.createdBy=?1 and a.name='ROLE_TEACHER')")
  List<User> findAllByCreatedBy(String login, String authorities);

  @Query("select u from User u inner join u.authorities a where u.createdBy=?1 and a.name=?2")
  List<User> findAllTeacherByCreatedBy(String login, String authorities);


}
