package com.school.management.util;

import java.util.Iterator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class RoleToIdUtil {
  public int roleToId(Authentication authentication) {
    final String authorities;
    Iterator<GrantedAuthority> itr =
        (Iterator<GrantedAuthority>) authentication.getAuthorities().iterator();
    authorities = itr.next().getAuthority();
    // logger.info(authorities);

    int roleId;
    if (authorities.equals("ROLE_principal"))
      roleId = 1;
    else if (authorities.equals("ROLE_teacher"))
      roleId = 2;
    else
      roleId = 3;
    return roleId;
  }
}
