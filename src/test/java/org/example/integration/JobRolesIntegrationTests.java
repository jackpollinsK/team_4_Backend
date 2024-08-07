package org.example.integration;

import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.example.JDDApplication;
import org.example.JDDConfiguration;
import org.example.daos.ApplicationDao;
import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.InvalidException;
import org.example.models.JobRoleRequest;
import org.example.models.LoginRequest;

import org.example.services.AuthService;
import org.example.validators.JobRoleValidator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;


import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;


@ExtendWith(DropwizardExtensionsSupport.class)
public class JobRolesIntegrationTests {
    public static final DropwizardAppExtension<JDDConfiguration> APP =
            new DropwizardAppExtension<>(JDDApplication.class);
    AuthService authService = Mockito.mock(AuthService.class);
    private static final String EMAIL = System.getenv("LOGIN_EMAIL_1");
    private static final String PASSWORD = System.getenv("LOGIN_PASSWORD_1");
    private static final String EMAIL2 = System.getenv("LOGIN_EMAIL_2");
    private static final String PASSWORD2 = System.getenv("LOGIN_PASSWORD_2");
    static String dateS = "2024-09-28";
    static Date date = Date.valueOf(dateS);
    private static final JobRoleRequest jobRoleRequest = new JobRoleRequest(
            "UD Test Designer",
            1,
            2,
            3,
            date,
            "Hi".repeat(1000),
            "Hi".repeat(500),
            "www.kainos.com"

    );
    private static final LoginRequest loginRequest = new LoginRequest(
            EMAIL,
            PASSWORD
    );
    private static final LoginRequest loginRequest2 = new LoginRequest(
            EMAIL2,
            PASSWORD2
    );


    @Test
    void getJobRoles_shouldReturn401Unauthorised_WhenUserNotLoggedIn() {
        Client client = APP.client();

        int response = client
                .target("http://localhost:8080/api/JobRoles")
                .request()
                .get().getStatus();

        Assertions.assertEquals(401, response);

    }

    @Test
    void getJobRoleById_shouldReturnJobRoleInfo_whenJobRoleInfoDoesExist_withAuthorisedUser() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest));

        int response = client
                .target("http://localhost:8080/api/JobRoles/1")
                .request().header("Authorization", "Bearer "
                        + token.readEntity(String.class)).get().getStatus();

        Assertions.assertEquals(200, response);
    }

    @Test
    void getJobRoleById_shouldReturn404_whenJobRoleInfoDoesNotExist_withAuthorisedUser() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest));

        int response = client
                .target("http://localhost:8080/api/JobRoles/99999")
                .request().header("Authorization", "Bearer "
                        + token.readEntity(String.class)).get().getStatus();

        Assertions.assertEquals(404, response);
    }

    @Test
    void getJobRoleById_shouldReturn401_whenJobRoleInfoDoesExist_withUnauthorisedUser() {
        Client client = APP.client();

        int response = client
                .target("http://localhost:8080/api/JobRoles/1")
                .request().get().getStatus();

        Assertions.assertEquals(401, response);
    }

    @Test
    void getJobRoles_shouldReturn200_WhenUserIsAuthorised() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest));

        int response = client
                .target("http://localhost:8080/api/JobRoles")
                .request().header("Authorization", "Bearer "
                        + token.readEntity(String.class)).get()
                .getStatus();
        Assertions.assertEquals(200, response);
    }

    @Test
    void deleteJobRole_shouldReturn404_WhenUserIsAuthorisedAndDeleteNotFound() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest2));

        int response = client
                .target("http://localhost:8080/api/JobRoles/4000")
                .request().header("Authorization", "Bearer "
                        + token.readEntity(String.class)).delete()
                .getStatus();
        Assertions.assertEquals(404, response);

    }

    @Test
    void addJobRole_shouldReturn403_WhenUserIsNotAdmin() {
        Client client = APP.client();

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest));

        int response = client
                .target("http://localhost:8080/api/JobRoles/")
                .request().header("Authorization", "Bearer "
                        + token.readEntity(String.class))
                .post(Entity.json(jobRoleRequest))
                .getStatus();
        Assertions.assertEquals(403, response);
    }

    @Test
    void addJobRole_shouldReturn401_WhenNotLoggedIn() {
        Client client = APP.client();


        int response = client
                .target("http://localhost:8080/api/JobRoles/")
                .request()
                .post(Entity.json(jobRoleRequest))
                .getStatus();
        Assertions.assertEquals(401, response);
    }

    @Test
    void addJobRole_shouldReturn201_WhenUserIsAdded()
            throws DatabaseConnectionException, SQLException, InvalidException {
        Client client = APP.client();
        JobRoleDao jobRoleDao = new JobRoleDao();
        DatabaseConnector databaseConnector = new DatabaseConnector();
        JobRoleValidator jobRoleValidator =
                Mockito.mock(JobRoleValidator.class);

        doNothing().when(jobRoleValidator).validateJobRole(jobRoleRequest);

        Response token = client
                .target("http://localhost:8080/api/auth/login")
                .request().post(Entity.json(loginRequest2));

        Response response = client
                .target("http://localhost:8080/api/JobRoles/").request()
                .header("Authorization", "Bearer "
                        + token.readEntity(String.class))
                .post(Entity.json(jobRoleRequest));

        int id = jobRoleDao.getMaxId(databaseConnector.getConnection());
        if (id == -1) {
            fail("Can not get max Id");
        }
        int response2 = client
                .target("http://localhost:8080/api/JobRoles/" + id)
                .request().delete()
                .getStatus();
        System.out.println(response.toString());
        Assertions.assertEquals(201, response.getStatus());
    }


}
