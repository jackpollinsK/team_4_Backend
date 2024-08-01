package org.example.controller;

import org.example.controllers.JobRoleController;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;
import org.example.services.JobRoleService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class JobRolesControllerTests {
    private final JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    private final JobRoleService jobRoleService = Mockito.mock(JobRoleService.class);
    private final JobRoleController jobRoleController = new JobRoleController(jobRoleService);

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
}
