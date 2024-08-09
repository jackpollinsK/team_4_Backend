package org.example.services;

import org.example.daos.CapabilityDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Capability;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CapabilityServiceTests {

    CapabilityDao capabilityDao = Mockito.mock(CapabilityDao.class);
    DatabaseConnector databaseConnector = Mockito.mock(DatabaseConnector.class);
    CapabilityService capabilityService = new CapabilityService(capabilityDao, databaseConnector);

    Connection conn;

    @Test
    void getCapabilities_shouldReturnCapabilities_whenDaoReturnsCapabilities()
            throws SQLException, DatabaseConnectionException {
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        List<Capability> expected = List.of(new Capability(1,
                "Software Engineer"));

        Mockito.when(capabilityDao.getAllCapabilities(conn)).thenReturn(expected);

        List<Capability> result = capabilityService.getCapabilities();

        assertEquals(expected, result);
    }

    @Test
    void getCapabilitys_shouldThrowSQLException_whenDaoThrowsSqlException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(capabilityDao.getAllCapabilities(conn)).thenThrow(SQLException.class);

        assertThrows(SQLException.class, () -> capabilityService.getCapabilities());
    }

    @Test
    void getCapabilities_shouldThrowDatabaseConnectionException_whenDaoThrowsDatabaseConnectionException()
            throws SQLException, DatabaseConnectionException{
        Mockito.when(databaseConnector.getConnection()).thenReturn(conn);

        Mockito.when(capabilityDao.getAllCapabilities(conn)).thenThrow(DatabaseConnectionException.class);

        assertThrows(DatabaseConnectionException.class, () -> capabilityService.getCapabilities());
    }
}
