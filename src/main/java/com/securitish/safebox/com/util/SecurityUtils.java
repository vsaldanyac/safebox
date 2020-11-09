package com.securitish.safebox.com.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SecurityUtils {
  public static final String HEADER = "Authorization";
  public static final String PREFIX = "Bearer ";
  public static final String SECRET = "security-ish_safebox";
  public static final long EXPIRATION_TIME = 180000;
  public static final int MAX_ERROR_NUMBER = 3;


  public static String getJWTToken(String username) {
    List<GrantedAuthority> grantedAuthorities = AuthorityUtils
        .commaSeparatedStringToAuthorityList("ROLE_USER");

    String token = Jwts
        .builder()
        .setId("security-ish")
        .setSubject(username)
        .claim("authorities",
            grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512,
            SECRET.getBytes()).compact();

    return "Bearer " + token;
  }
}
