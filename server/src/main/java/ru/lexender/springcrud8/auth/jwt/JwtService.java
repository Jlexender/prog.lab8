package ru.lexender.springcrud8.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.lexender.springcrud8.auth.userdata.Userdata;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Log4j2
@Service
@PropertySource("classpath:application.properties")
public class JwtService {
    SecretKey secretKey;
    Long duration;
    Long RTduration;

    public JwtService(SecretKey secretKey,
                      @Value("${crudserver.jwt.token.duration}") Long duration,
                      @Value("${crudserver.jwt.rt.duration}") Long RTduration) {
        this.secretKey = secretKey;
        this.duration = duration;
        this.RTduration = RTduration;
        log.info("JwtService.duration: {}", duration);
    }

    public String generateToken(Userdata user) {
        return Jwts.
                builder()
                .claim("sub", user.getUsername())
                .claim("typ", "access")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(duration)))
                .signWith(secretKey)
                .compact();
    }

    public String generateToken(String user) {
        return Jwts.
                builder()
                .claim("sub", user)
                .claim("typ", "access")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(duration)))
                .signWith(secretKey)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        if (isTokenExpired(token)) return false;
        String username = extractSubject(token);
        return (username.equals(userDetails.getUsername()));
    }

    <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractClaims(token));
    }

    Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isRefreshToken(String token) {
        return extractClaims(token).get("typ").equals("refresh");
    }

    public boolean isAccessToken(String token) {
        return extractClaims(token).get("typ").equals("access");
    }

    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateRT(String username) {
        return Jwts.
                builder()
                .claim("sub", username)
                .claim("typ", "refresh")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(RTduration)))
                .signWith(secretKey)
                .compact();
    }

}
