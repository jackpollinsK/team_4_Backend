package org.example.services;

import org.example.daos.ApplicationDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.validators.ApplicationValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.sql.Connection;
import java.sql.SQLException;

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

    @Test
    void createApplication_shouldThrowSQLEXception_whenDatabaseThrowsSQLException()
            throws SQLException, DatabaseConnectionException, InvalidException {
        ApplicationRequest applicationRequest = new ApplicationRequest(
                "adam@random.com",
                2,
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
        );

        Mockito.when(databaseConnector.getConnection())
                .thenReturn(conn);

        assertThrows(SQLException.class,
                () -> applicationService.createApplication(applicationRequest));

    }
}
