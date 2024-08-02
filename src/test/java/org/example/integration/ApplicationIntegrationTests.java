package org.example.integration;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.JDDApplication;
import org.example.JDDConfiguration;
import org.example.models.ApplicationRequest;
import org.example.models.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;


import javax.validation.constraints.Email;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/*The TestMethodOrder ensures they are run they are supposed to
*   1. 201 code - successful
*   2. 400 code - User has already applied
*/

@ExtendWith(DropwizardExtensionsSupport.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApplicationIntegrationTests {
    private static final DropwizardAppExtension<JDDConfiguration> APP =
            new DropwizardAppExtension<>(JDDApplication.class);

    private static final String EMAIL = System.getenv("LOGIN_EMAIL_2");
    private static final String PASSWORD = System.getenv("LOGIN_PASSWORD_2");

    private static final LoginRequest loginRequest = new LoginRequest(
            EMAIL,
            PASSWORD
    );

    ApplicationRequest applicationRequest = new ApplicationRequest(
            EMAIL,
            2,
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
    );

    @Test
    @Order(1)
    void apply_ShouldReturn200() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest));

        int status = client
                .target("http://localhost:8080/api/apply-for-role")
                .request().header(
                        "Authorization", "Bearer "
                                + token.readEntity(String.class))
                .post(Entity.json(applicationRequest))
                .getStatus();
        Assertions.assertEquals(201, status);

    }

    @Test
    @Order(2)
    void apply_ShouldReturn400BadRequest() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest));

        int status = client
                .target("http://localhost:8080/api/apply-for-role")
                .request().header(
                        "Authorization", "Bearer "
                                + token.readEntity(String.class))
                .post(Entity.json(applicationRequest))
                .getStatus();

        Assertions.assertEquals(400, status);

    }
}
