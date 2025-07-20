package org.example.librarymanagementsystem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.example.librarymanagementsystem.model.SystemUser;
import org.springframework.security.core.userdetails.UserDetails; // ✅ FIX: Add this import
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    private final String jwtSecret = "ThisIsASuperSecretKeyThatShouldBeStoredInEnvOrVault123!";
    private final long jwtExpirationMs = 86400000; // 1 day

    private final Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

    public String generateToken(SystemUser user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles().stream()
                        .map(role -> role.getName().name()) // Assumes RoleType enum
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public Claims getAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody();
    }

    public String extractUsername(String token) {
        return getAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return getAllClaims(token).getExpiration().before(new Date());
    }
}
