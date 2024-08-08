package org.example.services;

import org.example.daos.ApplicationDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.InvalidException;
import org.example.models.Application;
import org.example.models.ApplicationRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTests {

    private static final String EMAIL = System.getenv("LOGIN_EMAIL_1");
    ApplicationDao applicationDao = Mockito.mock(ApplicationDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    ApplicationService applicationService =
            new ApplicationService(applicationDao, databaseConnector);

    Connection conn;

    private static final ApplicationRequest applicationRequest =
            new ApplicationRequest(
                    "adam@random.com",
                    2,
                    "https://www.youtube.com/watch?v=dQw4w9WgXcQ");

    @Test
    void createApplication_shouldThrowSQLException_whenDatabaseThrowsSQLException()
            throws SQLException, DatabaseConnectionException, InvalidException {


        Mockito.when(databaseConnector.getConnection())
                .thenThrow(SQLException.class);

        assertThrows(SQLException.class,
                () -> applicationService.createApplication(applicationRequest));

    }

    @Test
    void createApplication_shouldThrowDatabaseConnectionException_whenDatabaseThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException, InvalidException {

        Mockito.when(databaseConnector.getConnection())
                .thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class,
                () -> applicationService.createApplication(applicationRequest));

    }

    @Test
    void createApplication_shouldThrowInvalidException_whenUserHasAlreadyApplied()
            throws SQLException, DatabaseConnectionException, InvalidException {

        Mockito.when(applicationDao.alreadyApplied(applicationRequest,
                        databaseConnector.getConnection()))
                .thenReturn(true);
        assertThrows(InvalidException.class,
                () -> applicationService.createApplication(
                        applicationRequest));

    }

    @Test
    void getAllApplications_shouldReturnJApplications_whenDaoReturnsApplications()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        List<Application> expected = List.of(new Application("test@gmail.com",
                1,
                "Belfast"));

        Mockito.when(applicationDao.getAllApplications(EMAIL, conn))
                .thenReturn(expected);

        List<Application> result =
                applicationService.getAllApplications(EMAIL);

        assertEquals(expected, result);
    }

    @Test
    void getAllApplications_shouldThrowSQLException_whenDaoThrowsSQLException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        List<Application> expected = List.of(new Application("test@gmail.com",
                1,
                "Belfast"));

        Mockito.when(applicationDao.getAllApplications(EMAIL, conn))
                .thenThrow(SQLException.class);


        assertThrows(SQLException.class,
                () -> applicationService.getAllApplications(EMAIL));
    }

    @Test
    void getAllApplications_shouldThrowDatabaseConnectionException_whenDaatabaseConnectorThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenThrow(DatabaseConnectionException.class);

        List<Application> expected = List.of(new Application("test@gmail.com",
                1,
                "Belfast"));


        assertThrows(DatabaseConnectionException.class,
                () -> applicationService.getAllApplications(EMAIL));
    }
}
