package org.example.controller;

import org.example.controllers.BandController;
import org.example.daos.BandDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Band;
import org.example.services.BandService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BandControllerTests {

    private final BandService bandService = Mockito.mock(BandService.class);
    private final BandController bandController =
            new BandController(bandService);

    @Test
    void getBandsShouldReturnBandsWhenServiceReturnsBands()
            throws SQLException, DatabaseConnectionException {
        List<Band> bandList = new ArrayList<>();
        Band band = new Band(1,
                "Apprentice");
        bandList.add(band);

        when(bandService.getBands()).thenReturn(bandList);

        Response response = bandController.getBands();

        assertEquals(200, response.getStatus());
        assertEquals(bandList, response.getEntity());
    }

    @Test
    void getBandsShouldReturn500WhenServiceThrowsSQLException()
            throws SQLException, DatabaseConnectionException {
        when(bandService.getBands()).thenThrow(SQLException.class);

        Response response = bandController.getBands();

        assertEquals(500, response.getStatus());
    }

    @Test
    void getBandsShouldReturn500WhenServiceThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        when(bandService.getBands()).thenThrow(DatabaseConnectionException.class);

        Response response = bandController.getBands();

        assertEquals(500, response.getStatus());
    }
}
