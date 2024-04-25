package com.edcarlitos.conctactapi.service;

import com.edcarlitos.conctactapi.entity.UserEntity;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    @Value("${key.secret.jwt}")
    private String secretKey;


    public String generateToken(UserEntity user){
        Map<String, Object> claims = new HashMap<>();

        claims.put("email",user.getEmail());
        claims.put("userId",user.getId());

        return buildToken(user.getUsername(),claims);
    }


    private String buildToken(String username, Map<String, ?> claims) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(username)
                .signWith(getSecretKey(), SignatureAlgorithm.HS256);

        if (claims != null) {
            jwtBuilder.setClaims(claims);
        }

        return jwtBuilder.compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("email",String.class);
    }

    private Key getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
