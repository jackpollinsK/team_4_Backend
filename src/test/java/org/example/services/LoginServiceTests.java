package org.example.services;

import io.jsonwebtoken.Jwts;
import org.example.daos.AuthDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;
import org.example.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.Key;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LoginServiceTests {

    Key jwtKey = Jwts.SIG.HS256.key().build();
    AuthDao authDao = Mockito.mock(AuthDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    AuthService authService = new AuthService(jwtKey, authDao, databaseConnector);

    private static final String EMAIL   = System.getenv("LOGIN_EMAIL_1");
    private static final String PASSWORD  = System.getenv("LOGIN_PASSWORD_1");

    LoginRequest loginRequest = new LoginRequest(
            EMAIL,
            PASSWORD
    );

    Connection conn;

    @Test
    void login_Return200Status_WhenLoginSuccessful() throws SQLException,
            DatabaseConnectionException, DoesNotExistException{
        User user = new User(EMAIL,"","",1);
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.getUser(loginRequest, conn)).thenReturn(user);
        User actual = authDao.getUser(loginRequest,conn);
        assertEquals(EMAIL,actual.getEmail());
    }



    @Test
    void login_ReturnSqlException_WhenDaoThrowsSqlException() throws
            SQLException, DatabaseConnectionException, DoesNotExistException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.getUser(loginRequest, conn))
                .thenThrow(SQLException.class);
        assertThrows(SQLException.class,
                () -> authService.login(loginRequest));
    }

    @Test
    void login_ThrowsDatabaseConnectionException_WhenDaoThrowsDatabaseConnectionException()
            throws
            SQLException, DatabaseConnectionException, DoesNotExistException {
        Mockito.when(authDao.getUser(loginRequest, conn)).thenThrow(
                DatabaseConnectionException.class);
        assertThrows(DatabaseConnectionException.class,
                () -> authService.login(loginRequest));

    }

    @Test
    void login_Return400_UserNotExist() throws SQLException,
            DatabaseConnectionException, DoesNotExistException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.getUser(loginRequest,conn)).thenThrow(DoesNotExistException.class);
        assertThrows(DoesNotExistException.class,
                () -> authService.login(loginRequest));

    }
    @Test
    void login_ReturnInvalidException() throws SQLException,
            DatabaseConnectionException, DoesNotExistException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);
        Mockito.when(authDao.getUser(loginRequest,conn)).thenReturn(null);
        assertThrows(InvalidException.class,
                () ->authService.login(loginRequest));
    }


}
