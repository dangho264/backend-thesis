package com.thesis.userservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;

@Component
public class JwtService {


  public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


  public Authentication validateToken(final String token) {
    try {
      // Kiểm tra và giải mã JWT ở đây
      Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
      // Nếu hợp lệ, trả về đối tượng Authentication
      // Ở đây, bạn có thể tạo một đối tượng Authentication từ claims
      // Ví dụ:
      String username = claims.getBody().getSubject();
      return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
    } catch (Exception e) {
      // Xử lý lỗi hoặc ném ngoại lệ nếu mã JWT không hợp lệ
      throw new BadCredentialsException("Invalid token");
    }
  }

  public String generateToken(String userName) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  private String createToken(Map<String, Object> claims, String userName) {
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(userName)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
            .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}