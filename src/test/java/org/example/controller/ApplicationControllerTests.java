package org.example.controller;

import org.example.controllers.ApplicationController;
import org.example.exceptions.DatabaseConnectionException;

import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.services.ApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.example.models.Application;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ApplicationControllerTests {

    private static final String EMAIL = System.getenv("LOGIN_EMAIL_1");

    ApplicationService applicationService =
            Mockito.mock(ApplicationService.class);

    ApplicationController applicationController =
            new ApplicationController(applicationService);

    private static final ApplicationRequest applicationRequest =
            new ApplicationRequest(
                    "adam@random.com",
                    2,
                    "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
            );

    @Test
    void apply_shouldReturn201_whenSuccessful()
            throws DatabaseConnectionException, SQLException, InvalidException {

        doNothing().when(applicationService)
                .createApplication(applicationRequest);
        Response response = applicationController.apply(applicationRequest);
        assertEquals(201, response.getStatus());
    }

    @Test
    void apply_shouldReturn400_whenInvalidException()
            throws DatabaseConnectionException, SQLException, InvalidException {

        ApplicationRequest wrongApplicationRequest = new ApplicationRequest(
                null,
                2,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        );

        doThrow(new InvalidException(Entity.APPLICATION,
                "You must enter an Email"))
                .when(applicationService)
                .createApplication(wrongApplicationRequest);

        Response response =
                applicationController.apply(wrongApplicationRequest);
        assertEquals(400, response.getStatus());
    }

    @Test
    void apply_shouldReturn500_whenSQLExceptionException()
            throws DatabaseConnectionException, SQLException, InvalidException {

        doThrow(new SQLException())
                .when(applicationService)
                .createApplication(applicationRequest);

        Response response = applicationController.apply(applicationRequest);
        assertEquals(500, response.getStatus());
    }

    @Test
    void apply_shouldReturn500_whenDatabaseConnectionError()
            throws DatabaseConnectionException, SQLException, InvalidException {

        doThrow(new DatabaseConnectionException(null))
                .when(applicationService)
                .createApplication(applicationRequest);

        Response response = applicationController.apply(applicationRequest);
        assertEquals(500, response.getStatus());
    }

    @Test
    void getApplicationsShouldReturnApplicationsWhenServiceReturnsApplications()
            throws SQLException, DatabaseConnectionException {
        List<Application> applicationList = new ArrayList<>();
        Application application = new Application("Open Test Role",
                1,
                "open");
        applicationList.add(application);

        when(applicationService.getAllApplications(EMAIL)).thenReturn(
                applicationList);

        Response response = applicationController.getAppliedJobs(EMAIL);

        assertEquals(200, response.getStatus());
        assertEquals(applicationList, response.getEntity());
    }

    @Test
    void getJobRolesShouldReturn500WhenServiceThrowsSQLException()
            throws SQLException, DatabaseConnectionException {
        when(applicationService.getAllApplications(EMAIL)).thenThrow(
                SQLException.class);

        Response response = applicationController.getAppliedJobs(EMAIL);

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRolesShouldReturn500WhenServiceThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        when(applicationService.getAllApplications(EMAIL)).thenThrow(
                DatabaseConnectionException.class);

        Response response = applicationController.getAppliedJobs(EMAIL);

        assertEquals(500, response.getStatus());
    }
}
