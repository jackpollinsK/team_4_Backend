package org.example.controller;
import org.example.controllers.AuthController;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.InvalidException;
import org.example.models.LoginRequest;
import org.example.services.AuthService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthControllerTest {
    AuthService authService = Mockito.mock(AuthService.class);
    private final AuthController authController = new AuthController(authService);

    private static final String EMAIL   = System.getenv("LOGIN_EMAIL_1");
    private static final String PASSWORD  = System.getenv("LOGIN_PASSWORD_1");

    private static final LoginRequest loginRequest = new LoginRequest(
                EMAIL,
                PASSWORD
    );

    @Test
    void login_shouldReturn200_whenSuccessful()
            throws DatabaseConnectionException, SQLException,
            DoesNotExistException, InvalidException {
        String jwtToken = "123";

        when(authService.login(loginRequest)).thenReturn(jwtToken);

        Response response = authController.login(loginRequest);

        assertEquals(200, response.getStatus());
        assertEquals(jwtToken, response.getEntity());
    }

    @Test
    void login_shouldReturn404_whenInvalid()
            throws DatabaseConnectionException, SQLException,
            DoesNotExistException, InvalidException {

        LoginRequest loginRequest2 = new LoginRequest(
                "notreal@random.com",
                "password321"
        );

        when(authService.login(loginRequest2)).thenThrow(DoesNotExistException.class);

        Response response = authController.login(loginRequest2);

        assertEquals(404, response.getStatus());
    }

    @Test
    void login_shouldReturn400_whenInvalid()
            throws DatabaseConnectionException, SQLException,
            DoesNotExistException, InvalidException {
        LoginRequest loginRequest3 = new LoginRequest(
                "adam@random.com",
                "invalidPassword"
        );

        when(authService.login(loginRequest3)).thenThrow(InvalidException.class);

        Response response = authController.login(loginRequest3);

        assertEquals(400, response.getStatus());
    }

    @Test
    void login_shouldReturn500_whenInvalid()
            throws DatabaseConnectionException, SQLException,
            DoesNotExistException, InvalidException {
        LoginRequest loginRequest4 = new LoginRequest(
                "",
                ""
        );

        when(authService.login(loginRequest4)).thenThrow(SQLException.class);

        Response response = authController.login(loginRequest4);

        assertEquals(500, response.getStatus());
    }

    @Test
    void generateUsers_shouldReturn200_whenSuccessful()
            throws DatabaseConnectionException, SQLException {
        String message = "generated two users.";

        when(authService.generateUsers()).thenReturn(message);

        Response response = authController.generateUsers();

        assertEquals(200, response.getStatus());
        assertEquals(message, response.getEntity());
    }

    @Test
    void generateUser_shouldReturn500_whenInvalid()
            throws DatabaseConnectionException, SQLException {

        when(authService.generateUsers()).thenThrow(SQLException.class);

        Response response = authController.generateUsers();

        assertEquals(500, response.getStatus());
    }


}