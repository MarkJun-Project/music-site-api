package com.music.admin.security.component;

import com.music.admin.security.properties.JwtProperties;
import com.music.admin.security.user.AuthenticationUserException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

import java.util.Date;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.lang.Assert.notNull;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Component
@RequiredArgsConstructor
public class JwtComponent {
    private final JwtProperties jwtProperties;

    public String issue(String id, JwtType type) {
        val now = new Date();
        val expiration = new Date(now.getTime() + jwtProperties.getTokenExpireTime(type));

        return Jwts.builder()
                .setSubject("music User " + type.name() + " Api Token")
                .setIssuer("music")
                .setIssuedAt(now)
                .setId(id.toString())
                .setExpiration(expiration)
                .signWith(HS256, jwtProperties.getEncodedSecretKey())
                .compact();
    }

    public String getId(String token) {
        try {
            return String.valueOf(Jwts.parser().setSigningKey(jwtProperties.getEncodedSecretKey()).parseClaimsJws(token).getBody().getId());
        } catch (ExpiredJwtException ex) {
            return String.valueOf(ex.getClaims().getId());
        } catch (Exception ex) {
            throw new AuthenticationUserException("Invalid Signature", UNAUTHORIZED);
        }
    }

    public String resolveToken(HttpServletRequest req, JwtType type) {
        String header = null;
        if (type == JwtType.ACCESS) {
            header = req.getHeader("Authorization");
        } else if (type == JwtType.REFRESH) {
            header = req.getHeader("X-Refresh-Token");
        }

        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    public void isExpired(String token) {
        try {
            notNull(Jwts.parser().setSigningKey(jwtProperties.getEncodedSecretKey()).parseClaimsJws(token).getBody());

            throw new AuthenticationUserException("Not expired access token", UNAUTHORIZED);
        } catch (ExpiredJwtException ignored) {
        } catch (IllegalArgumentException ex) {
            throw new AuthenticationUserException("Invalid access token", UNAUTHORIZED);
        }
    }

    public void validate(String token, JwtType type) {
        try {
            notNull(Jwts.parser().setSigningKey(jwtProperties.getEncodedSecretKey()).parseClaimsJws(token).getBody());
        } catch (ExpiredJwtException ex) {
            throw new AuthenticationUserException("Expired " + type.name() + " token", UNAUTHORIZED);
        } catch (Exception ex) {
            throw new AuthenticationUserException("Invalid " + type.name() + " token", UNAUTHORIZED);
        }
    }
}
