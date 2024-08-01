package org.example.auth;
import io.dropwizard.auth.AuthenticationException;
import io.jsonwebtoken.Jwts;

import io.jsonwebtoken.SignatureAlgorithm;
import org.example.models.JwtToken;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class JwtAuthenticatorTests {
    private JwtAuthenticator jwtAuthenticator;
    private Key key;

    @BeforeEach
    public void setUp() {
        String secret = "A_VERY_SECURE_SECRET_KEY_THAT_MEETS_THE_BYTE_REQ";
        key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256
                .getJcaName());
        jwtAuthenticator = new JwtAuthenticator(key);
    }

    @Test
    public void testAuthenticateValid() {
        // Create a JWT token with the correct key
        String token = Jwts.builder()
                .claim("Role", 1)
                .setSubject("user")
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        try {
            Optional<JwtToken> result = jwtAuthenticator.authenticate(token);
            Assertions.assertTrue(result.isPresent());
            Assertions.assertEquals(1,
                     result.get().getUserRole().getRoleId());
        } catch (AuthenticationException e) {
            Assertions.fail("AuthenticationException should not be thrown "
                    + "for valid token");
        }
    }

    @Test
    public void testAuthenticateInvalid() throws AuthenticationException {
        String token = "incorrectToken";

        Optional<JwtToken> result = jwtAuthenticator.authenticate(token);
        Assertions.assertFalse(result.isPresent());

    }
}
