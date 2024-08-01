package org.example.auth;

import io.dropwizard.auth.AuthenticationException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.models.JwtToken;
import org.example.models.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.Optional;

public class RoleAuthoriserTests {
    private JwtAuthenticator jwtAuthenticator;
    private RoleAuthoriser roleAuthoriser;
    private Key key;

    @BeforeEach
    public void setUp() {
        String secret = "A_VERY_SECURE_SECRET_KEY_THAT_MEETS_THE_BYTE_REQ";
        key = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS256
                .getJcaName());
        roleAuthoriser = new RoleAuthoriser();
    }

    @Test
    public void testAdminRoleAuthoriserValid() {
        String tokenString = Jwts.builder()
                .claim("Role", 1)
                .setSubject("user")
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        UserRole userRole = new UserRole(1);
        JwtToken jwtToken = new JwtToken(userRole);
        boolean result = false;
        result = roleAuthoriser.authorize(jwtToken,"Admin");
        Assertions.assertTrue(result);
    }

    @Test
    public void testUserRoleAuthoriserValid() {
        String tokenString = Jwts.builder()
                .claim("Role", 2)
                .setSubject("user")
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        UserRole userRole = new UserRole(2);
        JwtToken jwtToken = new JwtToken(userRole);
        boolean result = false;
        result = roleAuthoriser.authorize(jwtToken,"User");
        Assertions.assertTrue(result);
    }

    @Test
    public void testRoleAuthoriseInvalid() throws AuthenticationException {

        UserRole userRole1 = new UserRole(1);
        UserRole userRole2 = new UserRole(2);
        JwtToken jwtToken1 = new JwtToken(userRole1);
        JwtToken jwtToken2 = new JwtToken(userRole2);

        boolean result1 = false;
        result1 = roleAuthoriser.authorize(jwtToken1,"User");
        Assertions.assertFalse(result1);
        result1 = roleAuthoriser.authorize(jwtToken1,"invalid");
        Assertions.assertFalse(result1);

        boolean result2 = false;
        result2 = roleAuthoriser.authorize(jwtToken2,"Admin");
        Assertions.assertFalse(result2);
        result2 = roleAuthoriser.authorize(jwtToken2,"invalid");
        Assertions.assertFalse(result2);

    }

}
