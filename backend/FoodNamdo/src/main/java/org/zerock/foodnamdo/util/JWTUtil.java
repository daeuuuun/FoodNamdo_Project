package org.zerock.foodnamdo.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.zerock.foodnamdo.security.service.TokenBlacklistService;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
@RequiredArgsConstructor
public class JWTUtil {
    @Value("${org.zerock.jwt.secret}")
    private String key;

    @Value("${org.zerock.jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    @Value("${org.zerock.jwt.refresh-token-expiration-days}")
    private int refreshTokenExpirationDays;

    private final TokenBlacklistService tokenBlacklistService;

    public String generateToken(Map<String, Object> valueMap, int minute) {
//    public String generateToken(Map<String, Object> valueMap, int minute) {
        log.info("generateKey...." + key);

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.putAll(valueMap);

//        int time = (1) * minute;

        String jwjStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(payloads)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(minute).toInstant()))
                .signWith(SignatureAlgorithm.HS256, key.getBytes())
                .compact();

        return jwjStr;
    }
    public String generateAccessToken(Map<String, Object> valueMap) {
        return generateToken(valueMap, accessTokenExpirationMinutes);
    }

    public String generateRefreshToken(Map<String, Object> valueMap) {
        return generateToken(valueMap, refreshTokenExpirationDays * 24 * 60); // Convert days to minutes
    }

    public Map<String, Object> validateToken(String token) throws JwtException {
        if (tokenBlacklistService.isTokenBlacklisted(token)) {
            throw new JwtException("Token is blacklisted");
        }
        Map<String, Object> claim = null;

        claim = Jwts.parser()
                .setSigningKey(key.getBytes())
                .parseClaimsJws(token)
                .getBody();

        return claim;
    }

    public Long extractUserId(String token) throws JwtException {
        Claims claims = (Claims) validateToken(token);
        return ((Number) claims.get("userId")).longValue();
    }

}
