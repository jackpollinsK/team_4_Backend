package org.example.services;

import org.example.daos.ApplicationDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.validators.ApplicationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTests {

    ApplicationDao applicationDao = Mockito.mock(ApplicationDao.class);
    ApplicationValidator applicationValidator =
            Mockito.mock(ApplicationValidator.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    ApplicationService applicationService =
            new ApplicationService(applicationDao, applicationValidator,
                    databaseConnector);

    Connection conn;

    private static final ApplicationRequest applicationRequest =
            new ApplicationRequest(
                    "adam@random.com",
                    2,
                    "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
            );

    @Test
    void createApplication_shouldThrowSQLEXception_whenDatabaseThrowsSQLException()
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
    void createApplication_shouldThrowInvalidException_whenApplicationRequestInvalid()
            throws SQLException, DatabaseConnectionException, InvalidException {
        ApplicationRequest wrongApplicationRequest = new ApplicationRequest(
                null,
                2,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        );

        Mockito.doThrow(new InvalidException(Entity.APPLICATION,
                        "You must enter an Email"))
                .when(applicationValidator)
                .validateApplication(wrongApplicationRequest);

        assertThrows(InvalidException.class,
                () -> applicationService.createApplication(
                        wrongApplicationRequest));

    }
}
