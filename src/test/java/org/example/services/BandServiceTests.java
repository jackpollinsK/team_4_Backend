package org.example.services;

import org.example.daos.BandDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Band;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BandServiceTests {

    BandDao bandDao = Mockito.mock(BandDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    BandService bandService = new BandService(bandDao, databaseConnector);

    Connection conn;

    @Test
    void getBands_shouldReturnBands_whenDaoReturnsBands()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        List<Band> expected = List.of(new Band(1,
                "Apprentice"));

        Mockito.when(bandDao.getAllBands(conn)).thenReturn(expected);

        List<Band> result = bandService.getBands();

        assertEquals(expected, result);
    }

    @Test
    void getBands_shouldThrowSQLException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(bandDao.getAllBands(conn)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> bandService.getBands());
    }

    @Test
    void getBands_shouldThrowDatabaseConnectionException_whenDaoThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(bandDao.getAllBands(conn)).thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class, () -> bandService.getBands());
    }
}
