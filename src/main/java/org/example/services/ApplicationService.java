package org.example.services;

import org.example.daos.ApplicationDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;

import java.sql.SQLException;

public class ApplicationService {

    ApplicationDao applicationDao;
    DatabaseConnector databaseConnector;

    public ApplicationService(final ApplicationDao applicationDao,
                              final DatabaseConnector databaseConnector) {
        this.applicationDao = applicationDao;
        this.databaseConnector = databaseConnector;
    }

    public boolean createApplication(
            final ApplicationRequest applicationRequest)
            throws SQLException, InvalidException,
            DatabaseConnectionException {
        boolean applied = applicationDao.alreadyApplied(applicationRequest,
                databaseConnector.getConnection());

        if (applied) {
            throw new InvalidException(Entity.APPLICATION,
                    "You have already applied");
        } else {
            applicationDao.createApplication(applicationRequest,
                    databaseConnector.getConnection());
        }

        return true;


    }
}
