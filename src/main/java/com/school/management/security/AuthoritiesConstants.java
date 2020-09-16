package com.school.management.security;

import java.util.HashMap;
import java.util.Map;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

  public static Map<String, Integer> map;
  public static final String ADMIN = "ROLE_ADMIN";

  public static final String USER = "ROLE_USER";

  public static final String ANONYMOUS = "ROLE_ANONYMOUS";

  public static final String TEACHER = "ROLE_TEACHER";

  private AuthoritiesConstants() {}

  static {
    map = new HashMap<String, Integer>();
    map.put(AuthoritiesConstants.ADMIN, 3);
    map.put(AuthoritiesConstants.TEACHER, 2);
    map.put(AuthoritiesConstants.USER, 1);
  }
}
