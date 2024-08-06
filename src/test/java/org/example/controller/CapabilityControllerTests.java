package org.example.controller;

import org.example.controllers.CapabilityController;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Capability;
import org.example.services.CapabilityService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CapabilityControllerTests {

    private final CapabilityService capabilityService =
            Mockito.mock(CapabilityService.class);
    private final CapabilityController capabilityController =
            new CapabilityController(capabilityService);

    @Test
    void getCapabilitiesShouldReturnBandsWhenServiceReturnsBands()
            throws SQLException, DatabaseConnectionException {
        List<Capability> capabilityList = new ArrayList<>();
        Capability capability = new Capability(1,
                "Software Engineer");
        capabilityList.add(capability);

        when(capabilityService.getCapabilities()).thenReturn(capabilityList);

        Response response = capabilityController.getCapabilities();

        assertEquals(200, response.getStatus());
        assertEquals(capabilityList, response.getEntity());
    }

    @Test
    void getBandsShouldReturn500WhenServiceThrowsSQLException()
            throws SQLException, DatabaseConnectionException {
        when(capabilityService.getCapabilities()).thenThrow(SQLException.class);

        Response response = capabilityController.getCapabilities();

        assertEquals(500, response.getStatus());
    }

    @Test
    void getBandsShouldReturn500WhenServiceThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        when(capabilityService.getCapabilities()).thenThrow(
                DatabaseConnectionException.class);

        Response response = capabilityController.getCapabilities();

        assertEquals(500, response.getStatus());
    }
}
