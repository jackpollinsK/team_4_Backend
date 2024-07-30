package org.example.integration;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.JDDApplication;
import org.example.JDDConfiguration;
import org.example.models.ApplicationRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ApplicationIntegrationTests {
    private static final DropwizardAppExtension<JDDConfiguration> APP =
            new DropwizardAppExtension<>(JDDApplication.class);

    private static final String EMAIL = System.getenv("LOGIN_EMAIL_1");

    @Test
    void apply_ShouldReturn200() {
        Client client = APP.client();

        ApplicationRequest applicationRequest = new ApplicationRequest(
                EMAIL,
                2,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        );

        ;
        int status = client
                .target("http://localhost:8080/api/apply-for-role")
                .request().post(Entity.json(applicationRequest))
                .getStatus();
        Assertions.assertEquals(201, status);

    }

    @Test
    void apply_ShouldReturn400BadRequest() {
        Client client = APP.client();

        ApplicationRequest applicationRequest = new ApplicationRequest(
                null,
                1,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        );

        ;
        int status = client
                .target("http://localhost:8080/api/apply-for-role")
                .request().post(Entity.json(applicationRequest))
                .getStatus();
        Assertions.assertEquals(400, status);

    }
}
