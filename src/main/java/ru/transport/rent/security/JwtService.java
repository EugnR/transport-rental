package ru.transport.rent.security;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * JwtService.
 */
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.subject}")
    private String subject;

    /**
     * JwtService.
     */
    public String generateToken(String username, String role, int expiresIn) {
        Date expirationDate = Date.from(ZonedDateTime.now()
                .plusSeconds(expiresIn)
                .toInstant());
        return JWT.create()
                .withSubject(subject)
                .withClaim("username", username)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    /**
     * JwtService.
     */
    public Optional<String> validateTokenAndRetrieveClaim(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withSubject(subject)
                    .withIssuer(issuer)
                    .build();

            DecodedJWT decodedJWT = verifier.verify(token);

            return Optional.of(decodedJWT.getClaim("username")
                    .asString());
        } catch (JWTVerificationException e) {
            return Optional.empty();
        }
    }

}
