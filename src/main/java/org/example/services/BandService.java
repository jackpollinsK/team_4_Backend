package org.example.services;

import org.example.daos.BandDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Band;

import java.sql.SQLException;
import java.util.List;

public class BandService {

    private final BandDao bandDao;
    private final DatabaseConnector databaseConnector;

    public BandService(final BandDao bandDao,
                       final DatabaseConnector databaseConnector) {
        this.bandDao = bandDao;
        this.databaseConnector = databaseConnector;
    }

    public List<Band> getBands() throws SQLException,
            DatabaseConnectionException {
        return bandDao.getAllBands(databaseConnector.getConnection());
    }
}
