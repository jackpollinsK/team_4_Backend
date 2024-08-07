package org.example.services;

import org.example.daos.LocationDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Location;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocationServiceTests {

    LocationDao locationDao = Mockito.mock(LocationDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    LocationService locationService =
            new LocationService(locationDao, databaseConnector);

    Connection conn;

    @Test
    void getLocations_shouldReturnLocations_whenDaoReturnsLocations()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        List<Location> expected = List.of(new Location(1,
                "Belfast", "4-6 Upper Crescent", "12345678"));

        Mockito.when(locationDao.getAllLocations(conn)).thenReturn(expected);

        List<Location> result = locationService.getLocations();

        assertEquals(expected, result);
    }

    @Test
    void getLocations_shouldThrowSQLException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(locationDao.getAllLocations(conn))
                .thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> locationService.getLocations());
    }

    @Test
    void getLocations_shouldThrowDatabaseConnectionException_whenDaoThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(locationDao.getAllLocations(conn))
                .thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class,
                () -> locationService.getLocations());
    }
}
