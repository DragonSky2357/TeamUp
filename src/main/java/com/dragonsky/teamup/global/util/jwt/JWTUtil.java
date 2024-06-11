package com.dragonsky.teamup.global.util.jwt;

import com.dragonsky.teamup.auth.model.Refresh;
import com.dragonsky.teamup.auth.repository.RefreshRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JWTUtil {

    private Key key;

    @Value("${jwt.expire.access}")
    private long accessExpire;

    @Value("${jwt.expire.refresh}")
    private long refreshExpire;

    public JWTUtil(@Value("${jwt.secret}") String secret, RefreshRepository refreshRepository) {
        byte[] byteSecretKey = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(byteSecretKey);
    }

    public Claims getMemberInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public String getCategory(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("category", String.class);
    }

    public Long getId(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("id", Long.class);
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("username", String.class);
    }

    public String getRole(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getExpiration().before(new Date());
    }

    public String createJwt(String category, Long id, String username, String role) {
        return Jwts.builder()
                .setClaims(createClaims(category, id, username, role))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpire))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefresh(String category) {
        return Jwts.builder()
                .setClaims(createRefreshClaims(category))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims createClaims(String category, Long id, String username, String role) {
        Claims claims = Jwts.claims();
        claims.put("category", category);
        claims.put("id", id);
        claims.put("username", username);
        claims.put("role", role);
        return claims;
    }

    private Claims createRefreshClaims(String category) {
        Claims claims = Jwts.claims();
        claims.put("category", category);
        return claims;
    }



}
