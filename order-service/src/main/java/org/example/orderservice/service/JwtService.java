package org.example.orderservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;


@Service
public class JwtService {

    private static final String SECRET_KEY = "9e7e06efd144cc9ca4639f33c649f1a9d7b11a9df5baaa9b20eb88d1b1664ee3";

    public String extractUsername(String jwt) {
        Claims claims = extractAllClaims(jwt);
        return claims.get(Claims.SUBJECT, String.class);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
