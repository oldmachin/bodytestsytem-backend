package org.example.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String generateToken(Map<String, Object> claims, Authentication authentication) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date(now))
                .expiration(new Date(now + expiration))
                .subject(authentication.getName())
                .signWith(getKey())
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            final Claims claims = parseClaims(token);
            return true;
        } catch (SignatureException e) {
            System.err.println("Invalid JWT Signature: " + e.getMessage());
        }catch (MalformedJwtException e) {
            System.err.println("Invalid JWT token: "+ e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is invalid: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Other exception: " + e.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token) {
        final Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
