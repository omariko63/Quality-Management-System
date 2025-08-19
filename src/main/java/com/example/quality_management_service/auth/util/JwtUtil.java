package com.example.quality_management_service.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.function.Function;

public class JwtUtil {
    @Value("{$jwt.secret}")
    private String secret;

    @Value("{$jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("{$jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private String generateToken( String username, long expiration ) {
        return Jwts.builder()
                .setSubject(username)
                //.claim("roles", "admin")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
    public String generateAccessToken(String username){
        return generateToken(username, accessTokenExpiration);
    }
    public String generateRefreshToken(String username) {
       return generateToken(username, refreshTokenExpiration);
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public boolean validateToken(String token){
        return isTokenExpired(token);
    }
    public Date getExpirationDate(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Boolean isTokenExpired(String token){
       Date expiration = getExpirationDate(token);
       return expiration.before(new Date());
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }
}
