package com.example.demo.token;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

@Service
public class TokenService {
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; //1 hr
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email) //use current user email as validation for the token
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    //helper function to extract requested data from token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = parseToken(token);
        return claimsResolver.apply(claims);
    }


        public String extractCurrentUserEmail() {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7); // Remove "Bearer "
                return extractEmail(token);
            }

            throw new IllegalStateException("No valid JWT token found in Authorization header");
        }

    //get email from token
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //check if email in token is used as the one to sign in with
    public boolean isTokenValid(String token, String userEmail) {
        final String extractedEmail = extractEmail(token);
        return (extractedEmail.equals(userEmail) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }



    private Claims parseToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
