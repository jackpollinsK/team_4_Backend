package org.example.services;

import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.TestExecutionResult;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class JobRolesServiceTests {

    JobRoleDao jobRoleDao = Mockito.mock(JobRoleDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    JobRoleService jobRoleService =
            new JobRoleService(jobRoleDao, databaseConnector);

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
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getAllJobRoles(conn))
                .thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> jobRoleService.getJobRoles());
    }

    @Test
    void getJobRoles_shouldThrowDatabaseConnectionException_whenDaoThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getAllJobRoles(conn))
                .thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class,
                () -> jobRoleService.getJobRoles());
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

        Mockito.when(jobRoleDao.getJobRoleById(1, conn)).thenReturn(expected);

        JobRoleInfo result = jobRoleService.getJobRoleById(1);
        assertEquals(expected, result);
    }

    @Test
    void getJobRolesById_shouldReturnDoesNotExistException_whenDaoReturnsNull()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(jobRoleDao.getJobRoleById(1, conn)).thenReturn(null);

        assertThrows(DoesNotExistException.class,
                () -> jobRoleService.getJobRoleById(1));
    }

    @Test
    void getJobRoleById_shouldThrowSQLException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoleById(1, conn))
                .thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> jobRoleService.getJobRoleById(1));
    }

    @Test
    void getJobRoleById_shouldThrowDatabaseConnectionException_whenDaoThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(jobRoleDao.getJobRoleById(1, conn))
                .thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class,
                () -> jobRoleService.getJobRoleById(1));
    }

    @Test
    void deleteJobRole_shouldThrowSQLException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        int id = jobRoleDao.getMaxId(databaseConnector.getConnection());
        if (id == -1) {
            fail("Can not get max Id");
        }
        doThrow(SQLException.class).when(jobRoleDao)
                .deleteJobRole(id, conn);
        assertThrows(SQLException.class,
                () -> jobRoleDao.deleteJobRole(id,databaseConnector.getConnection()));
    }
    @Test
    void deleteJobRole_shouldThrowDatabaseException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(null);
        int id = jobRoleDao.getMaxId(databaseConnector.getConnection());
        if (id == -1) {
            fail("Can not get max Id");
        }
        doThrow(DatabaseConnectionException.class).when(jobRoleDao)
                .deleteJobRole(id, conn);
        assertThrows(DatabaseConnectionException.class,
                () -> jobRoleDao.deleteJobRole(id,databaseConnector.getConnection()));
    }
    @Test
    void deleteJobRole_shouldReturn204_whenRoleIsDeleted() {
        try{
            Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
            int id = jobRoleDao.getMaxId(databaseConnector.getConnection());
            if (id == -1) {
                fail("Can not get max Id");
            }
            jobRoleDao.deleteJobRole(id, conn);
        } catch (Exception e) {
            fail("Should not throw an error");
        }
    }
}
