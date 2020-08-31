package com.school.management.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.token.expirationTime}")
  private long minute;

  Logger logger = LoggerFactory.getLogger(JwtUtil.class);

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public String generateToken(Authentication authentication) {
    RoleToIdUtil roleUtil = new RoleToIdUtil();
    final int roleId = roleUtil.roleToId(authentication);
    Map<String, Object> claims = new HashMap<>();
    claims.put("roleId", roleId);
    // claims.put("userId", userDTO.getId());
    return createToken(claims, authentication.getName());
  }

  private String createToken(Map<String, Object> claims, String subject) {
    logger.info("===> create token");
    logger.info("secret key:" + secret);
    return Jwts.builder().setClaims(claims).setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + (minute * 60 * 1000)))
        .signWith(SignatureAlgorithm.HS256, secret).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
