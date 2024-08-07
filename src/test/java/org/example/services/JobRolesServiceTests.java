package org.example.services;
import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;
import org.example.models.JobRoleRequest;
import org.example.models.LoginRequest;
import org.example.validators.JobRoleValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(MockitoExtension.class)
class JobRolesServiceTests {

    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    JobRoleValidator jobRoleValidator = Mockito.mock(JobRoleValidator.class);
    JobRoleService jobRoleService = new JobRoleService(jobRoleDao, databaseConnector, jobRoleValidator);
    private static final String EMAIL = System.getenv("LOGIN_EMAIL_1");
    private static final String PASSWORD = System.getenv("LOGIN_PASSWORD_1");
    private static final String EMAIL2 = System.getenv("LOGIN_EMAIL_2");
    private static final String PASSWORD2 = System.getenv("LOGIN_PASSWORD_2");
    static String dateS = "2024-09-28";
    static java.sql.Date date = java.sql.Date.valueOf(dateS);
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
    Connection conn;
    @Test
    void getJobRoles_shouldReturnJobRoles_whenDaoReturnsJobRoles()
        throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        List<JobRole> expected = List.of(new JobRole(1,
                "Open Test Role",
                "Belfast",
                "Eng",
                "A",
                new Date(),
                "open"));

        Mockito.when(jobRoleDao.getAllJobRoles(conn)).thenReturn(expected);

        List<JobRole> result = jobRoleService.getJobRoles();

        assertEquals(expected, result);
    }

    @Test
    void getJobRoles_shouldThrowSQLException_whenDaoThrowsSqlException()
        throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getAllJobRoles(conn)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> jobRoleService.getJobRoles());
    }

    @Test
    void getJobRoles_shouldThrowDatabaseConnectionException_whenDaoThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getAllJobRoles(conn)).thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class, () -> jobRoleService.getJobRoles());
    }

    @Test
    void getJobRolesById_shouldReturnJobRolesInfo_whenDaoReturnsJobRolesInfo()
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        JobRoleInfo expected = new JobRoleInfo(1,
                "Open Test Role",
                "Belfast",
                "Eng",
                "A",
                new Date(),
                "open",
                "Test Description",
                "Test Responsibilities",
                "Test Job Spec");

        Mockito.when(jobRoleDao.getJobRoleById(1,conn)).thenReturn(expected);

        JobRoleInfo result = jobRoleService.getJobRoleById(1);
        assertEquals(expected, result);
    }
    @Test
    void getJobRolesById_shouldReturnDoesNotExistException_whenDaoReturnsNull()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoleById(1, conn)).thenReturn(null);

        assertThrows(DoesNotExistException.class, () -> jobRoleService.getJobRoleById(1));
    }
    @Test
    void getJobRoleById_shouldThrowSQLException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoleById(1,conn)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> jobRoleService.getJobRoleById(1));
    }

    @Test
    void getJobRoleById_shouldThrowDatabaseConnectionException_whenDaoThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoleById(1, conn)).thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class, () -> jobRoleService.getJobRoleById(1));
    }
    @Test
    void addJobRole_shouldThrowSQLException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.insertRole(jobRoleRequest,conn))
                .thenThrow(SQLException.class);

        assertThrows(SQLException.class, () ->
                jobRoleDao.insertRole(jobRoleRequest,conn));
    }

//    @Test
//    void addJobRole_shouldReturnCreatedRole_whenNewRoleCreated()
//            throws SQLException, DatabaseConnectionException{
//        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
//        JobRoleRequest expected = new JobRoleRequest(
//                "UD Test Designer",
//                1,
//                2,
//                3,
//                date,
//                "Hi".repeat(1000),
//                "Hi".repeat(500),
//                "www.kainos.com"
//        );
//        Mockito.when(jobRoleDao.insertRole(jobRoleRequest,conn))
//                .thenReturn(jobRoleRequest);
//        int id = jobRoleDao.getMaxId(databaseConnector.getConnection());
//        if (id == -1) {
//            fail("Can not get max Id");
//        }
//        Mockito.when(jobRoleDao.getJobRoleById(id,conn)).thenReturn(expected);
//        JobRoleRequest result =
//        assertEquals();




}
