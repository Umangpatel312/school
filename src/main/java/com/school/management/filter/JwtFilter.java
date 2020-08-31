package com.school.management.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import com.school.management.service.UserLoginService;
import com.school.management.util.JwtUtil;
import io.jsonwebtoken.Claims;

@Service
public class JwtFilter extends OncePerRequestFilter {

  Logger logger = LoggerFactory.getLogger(JwtFilter.class);

  private JwtUtil jwtUtil;

  private UserLoginService userLoginService;

  @Value("${jwt.token.secret}")
  private String secret;

  @Autowired
  public JwtFilter(JwtUtil jwtUtil, UserLoginService userLoginService) {
    this.jwtUtil = jwtUtil;
    this.userLoginService = userLoginService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {

    String authorizationHeader = httpServletRequest.getHeader("Authorization");

    String token = null;
    String userName = null;
    Integer roleId = null;

    logger.info("filter is invoked");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      token = authorizationHeader.substring(7);
      userName = jwtUtil.extractUsername(token);

    }

    if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = null;

      userDetails = userLoginService.loadUserByUsername(userName);

      if (jwtUtil.validateToken(token, userDetails)) {

        Function<Claims, Map<String, Object>> resolver =
            claims -> new HashMap<String, Object>(claims);
        Map<String, Object> map = jwtUtil.extractClaim(token, resolver);

        logger.info("map:" + map);

        roleId = (Integer) map.get("roleId");
        logger.info("authorities:" + userDetails.getAuthorities());
        httpServletRequest.setAttribute("username", userName);

        logger.info("roleid:" + roleId);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        usernamePasswordAuthenticationToken
            .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }

}
