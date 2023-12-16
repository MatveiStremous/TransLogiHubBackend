package com.example.commonservice.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.commonservice.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.access.secret}")
    private String accessSecret;
    @Value("${token.lifetime.in.minutes}")
    private Integer TOKEN_LIFETIME_IN_MINUTES;
    private final String SUBJECT = "User details";
    private final String ISSUER = "Matthew";

    public String generateAccessToken(User user) {
        Date expirationDate = Date.from(ZonedDateTime
                .now()
                .plusMinutes(TOKEN_LIFETIME_IN_MINUTES)
                .toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim("login", user.getLogin())
                .withClaim("role", user.getRole().toString())
                .withClaim("isActive", user.getIsActive())
                .withClaim("firstName", user.getFirstName())
                .withClaim("middleName", user.getMiddleName())
                .withClaim("lastName", user.getLastName())
                .withClaim("convoyId", user.getConvoy() == null ? null : user.getConvoy().getId())
                .withClaim("phone", user.getPhone())
                .withIssuedAt(new Date())
                .withIssuer(ISSUER)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(accessSecret));
    }

    public String getClaimFromToken(String jwtToken, String claimName) {
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        return decodedJWT.getClaim(claimName).asString();
    }

    public void validateToken(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(accessSecret))
                .withSubject(SUBJECT)
                .withIssuer(ISSUER)
                .withClaim("isActive", true)
                .build();
        verifier.verify(token);
    }

    public String getClaimFromToken(String claimName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            String authorizationHeader = attributes.getRequest().getHeader("Authorization");

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwtToken = authorizationHeader.substring(7);
                DecodedJWT decodedJWT = JWT.decode(jwtToken);
                return decodedJWT.getClaim(claimName).asString();
            }
        }
        return null;
    }
}
