package org.example.services;

import org.example.daos.CapabilityDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Capability;

import java.sql.SQLException;
import java.util.List;

public class CapabilityService {

    private final CapabilityDao capabilityDao;
    private final DatabaseConnector databaseConnector;

    public CapabilityService(final CapabilityDao capabilityDao,
                             final DatabaseConnector databaseConnector) {
        this.capabilityDao = capabilityDao;
        this.databaseConnector = databaseConnector;
    }

    public List<Capability> getCapabilities() throws SQLException,
            DatabaseConnectionException {
        return capabilityDao.getAllCapabilities(
                databaseConnector.getConnection());
    }
}
