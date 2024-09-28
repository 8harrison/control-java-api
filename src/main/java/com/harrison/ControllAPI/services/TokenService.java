package com.harrison.ControllAPI.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.harrison.ControllAPI.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    private final Algorithm algorithm;

    public TokenService(@Value("${api.security.token.secret}")String tokenSecret) {

        this.algorithm = Algorithm.HMAC256(tokenSecret);
    }

    public String generateToken(User user){
        return JWT.create()
                .withIssuer("mosteiroDelta")
                .withSubject(user.getUsername())
                .withExpiresAt(generateExpirationTime())
                .sign(algorithm);
    }

    private Instant generateExpirationTime() {
        return LocalDateTime.now()
                .plusHours(24)
                .toInstant(ZoneOffset.of("-3:00"));
    }

    public String validateToken(String token){
        return JWT.require(algorithm)
                .withIssuer("mosteiroDelta")
                .build()
                .verify(token)
                .getSubject();
    }
}
