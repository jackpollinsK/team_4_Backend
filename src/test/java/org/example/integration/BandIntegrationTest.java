package org.example.integration;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.JDDApplication;
import org.example.JDDConfiguration;
import org.example.models.LoginRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

@ExtendWith(DropwizardExtensionsSupport.class)
public class BandIntegrationTest {

    public static final DropwizardAppExtension<JDDConfiguration> APP =
            new DropwizardAppExtension<>(JDDApplication.class);

    private static final String EMAIL = System.getenv("LOGIN_EMAIL_1");
    private static final String PASSWORD = System.getenv("LOGIN_PASSWORD_1");
    private static final String EMAIL2 = System.getenv("LOGIN_EMAIL_2");
    private static final String PASSWORD2 = System.getenv("LOGIN_PASSWORD_2");

    private static final LoginRequest loginRequest = new LoginRequest(
            EMAIL,
            PASSWORD
    );

    private static final LoginRequest loginRequest2 = new LoginRequest(
            EMAIL2,
            PASSWORD2
    );

    @Test
    void getBands_shouldReturn401Unauthorised_WhenUserNotLoggedIn() {

        Client client = APP.client();

        int response = client
                .target("http://localhost:8080/api/band")
                .request()
                .get().getStatus();

        Assertions.assertEquals(401, response);

    }

    @Test
    void getBands_shouldReturn403_WhenUserIsNotAdmin() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest));

        int response = client
                .target("http://localhost:8080/api/band")
                .request().header("Authorization", "Bearer "
                        + token.readEntity(String.class)).get()
                .getStatus();
        Assertions.assertEquals(403, response);
    }


    @Test
    void getBands_shouldReturn200_WhenUserIsAuthorised() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest2));

        int response = client
                .target("http://localhost:8080/api/band")
                .request().header("Authorization", "Bearer "
                        + token.readEntity(String.class)).get()
                .getStatus();
        Assertions.assertEquals(200, response);
    }
}
