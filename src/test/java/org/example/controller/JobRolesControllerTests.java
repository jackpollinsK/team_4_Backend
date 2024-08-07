package org.example.controller;

import org.eclipse.jetty.http.HttpStatus;
import org.example.controllers.JobRoleController;
import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidException;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;
import org.example.models.JobRoleRequest;
import org.example.models.LoginRequest;
import org.example.services.JobRoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JobRolesControllerTests {
    private final JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    private final JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);
    private final JobRoleController jobRoleController = new JobRoleController(jobRoleService);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    static String dateS = "2024-09-28";
    static java.sql.Date date = java.sql.Date.valueOf(dateS);
    private static final JobRoleRequest jobRoleRequest = new JobRoleRequest(
            "UX Test Designer",
            1,
            2,
            3,
            date,
            "Fantastic Job",
            "Loads of Responsibilities",
            "www.kainos.com"
    );



    @Test
    void getJobRolesShouldReturnJobRolesWhenServiceReturnsJobRoles()
            throws SQLException, DatabaseConnectionException {
        List<JobRole> jobRoleList = new ArrayList<>();
        JobRole jobRole = new JobRole(1,
                "Open Test Role",
                "Belfast",
                "Eng",
                "A",
                new Date(),
                "open");
        jobRoleList.add(jobRole);

        when(jobRoleService.getJobRoles()).thenReturn(jobRoleList);

        Response response = jobRoleController.getJobRoles();

        System.out.println("Expected JobRoles: " + jobRoleList);
        System.out.println("Actual JobRoles: " + response.getEntity());

        assertEquals(200, response.getStatus());
        assertEquals(jobRoleList, response.getEntity());
    }

    @Test
    void getJobRolesShouldReturn500WhenServiceThrowsSQLException()
            throws SQLException, DatabaseConnectionException {
        when(jobRoleService.getJobRoles()).thenThrow(SQLException.class);

        Response response = jobRoleController.getJobRoles();

        System.out.println("Response Status: " + response.getStatus());

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRolesShouldReturn500WhenServiceThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        when(jobRoleService.getJobRoles()).thenThrow(DatabaseConnectionException.class);

        Response response = jobRoleController.getJobRoles();

        System.out.println("Response Status: " + response.getStatus());

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRoleByIdShouldReturnJobRoleInfoWhenServiceReturnsJobRoleInfo()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException {
        JobRoleInfo jobRoleInfo = new JobRoleInfo(1,
                "Open Test Role",
                "Belfast",
                "Eng",
                "A",
                new Date(),
                "open",
                "Test Description",
                "Test Responsibilities",
                "Test Job Spec");

        when(jobRoleService.getJobRoleById(1)).thenReturn(jobRoleInfo);

        Response response = jobRoleController.getJobRoleById(1);

        assertEquals(200, response.getStatus());
        assertEquals(jobRoleInfo, response.getEntity());
    }

    @Test
    void getJobRoleByIdShouldReturn404WhenServiceReturnsNull()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException {
        when(jobRoleService.getJobRoleById(1)).thenThrow(DoesNotExistException.class);
        Response response = jobRoleController.getJobRoleById(1);
        assertEquals(404, response.getStatus());
    }

    @Test
    void getJobRoleByIdShouldReturn500WhenServiceThrowsSQLException()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException {
        when(jobRoleService.getJobRoleById(1)).thenThrow(SQLException.class);

        Response response = jobRoleController.getJobRoleById(1);

        assertEquals(500, response.getStatus());
    }

    @Test
    void getJobRoleByIdShouldReturn500WhenServiceThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException {
        when(jobRoleService.getJobRoleById(1))
                .thenThrow(DatabaseConnectionException.class);

        Response response = jobRoleController.getJobRoleById(1);

        assertEquals(500, response.getStatus());
    }

    @Test
    void deleteJobRole_ShouldReturn204_WhenDeletePasses()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException, InvalidException {

        int id = 24;
        doNothing().when(jobRoleService).deleteJobRole(id);
        Response response = jobRoleController.deleteJobRole(id);
        assertEquals(204, response.getStatus());

    }
    @Test
    void deleteJobRole_ShouldReturn404_WhenIdDoesNotExist()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException, InvalidException {

        int id = 15000;
        doThrow(DoesNotExistException.class).when(jobRoleService)
                .deleteJobRole(id);
        Response response = jobRoleController.deleteJobRole(id);
        assertEquals(404, response.getStatus());

    }

    @Test
    void deleteJobRole_ShouldReturn500_WhenSQLExceptionThrown()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException, InvalidException {

        int id = 20;
        doThrow(SQLException.class).when(jobRoleService)
                .deleteJobRole(id);
        Response response = jobRoleController.deleteJobRole(id);
        assertEquals(500, response.getStatus());

    }

    @Test
    void deleteJobRole_ShouldReturn500_WhenDatabaseConnectionExceptionThrown()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException, InvalidException {

        int id = 20;
        doThrow(DatabaseConnectionException.class).when(jobRoleService)
                .deleteJobRole(id);
        Response response = jobRoleController.deleteJobRole(id);
        assertEquals(500, response.getStatus());

    }

}
