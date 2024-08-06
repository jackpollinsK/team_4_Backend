package org.example.controller;

import org.example.controllers.LocationController;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Location;
import org.example.services.LocationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LocationServiceTests {

    private final LocationService locationService =
            Mockito.mock(LocationService.class);
    private final LocationController locationController =
            new LocationController(locationService);

    @Test
    void getLocationsShouldReturnJobRolesWhenServiceReturnsJobRoles()
            throws SQLException, DatabaseConnectionException {
        List<Location> locationList = new ArrayList<>();
        Location location = new Location(1,
                "Belfast", "4-6 Upper Crescent", "12345678");
        locationList.add(location);

        when(locationService.getLocations()).thenReturn(locationList);

        Response response = locationController.getLocations();

        assertEquals(200, response.getStatus());
        assertEquals(locationList, response.getEntity());
    }

    @Test
    void getLocationsShouldReturn500WhenServiceThrowsSQLException()
            throws SQLException, DatabaseConnectionException {
        when(locationService.getLocations()).thenThrow(SQLException.class);

        Response response = locationController.getLocations();

        assertEquals(500, response.getStatus());
    }

    @Test
    void getBandsShouldReturn500WhenServiceThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        when(locationService.getLocations()).thenThrow(DatabaseConnectionException.class);

        Response response = locationController.getLocations();

        assertEquals(500, response.getStatus());
    }
}
