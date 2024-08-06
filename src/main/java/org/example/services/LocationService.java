package org.example.services;

import org.example.daos.DatabaseConnector;
import org.example.daos.LocationDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Location;

import java.sql.SQLException;
import java.util.List;

public class LocationService {

    private final LocationDao locationDao;
    private final DatabaseConnector databaseConnector;

    public LocationService(final LocationDao locationDao,
                           final DatabaseConnector databaseConnector) {
        this.locationDao = locationDao;
        this.databaseConnector = databaseConnector;
    }

    public List<Location> getLocations() throws SQLException,
            DatabaseConnectionException {
        return locationDao.getAllLocations(databaseConnector.getConnection());
    }
}
