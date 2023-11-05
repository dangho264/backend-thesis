package com.thesis.userservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.thesis.userservice.DTO.UserDto;
import com.thesis.userservice.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    private String secretKey = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    private final UserService userServices;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String login) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3_600_000);

        return JWT.create()
                .withSubject(login)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm)
                .build();

        DecodedJWT decoded = verifier.verify(token);
        UserDto user = userServices.findByUsername(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//
//    public Authentication validateToken(final String token) {
//        try {
//            // Kiểm tra và giải mã JWT ở đây
//            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
//            // Nếu hợp lệ, trả về đối tượng Authentication
//            // Ở đây, bạn có thể tạo một đối tượng Authentication từ claims
//            // Ví dụ:
//            String username = claims.getBody().getSubject();
//            UserDto user = userServices.findByUsername(username);
//            return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
//        } catch (Exception e) {
//            // Xử lý lỗi hoặc ném ngoại lệ nếu mã JWT không hợp lệ
//            throw new BadCredentialsException("Invalid token");
//        }
//    }
}
