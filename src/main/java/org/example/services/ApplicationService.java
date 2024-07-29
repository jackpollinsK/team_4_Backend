package org.example.services;

import org.example.daos.ApplicationDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.validators.ApplicationValidator;

import java.sql.SQLException;

public class ApplicationService {

    ApplicationDao applicationDao;
    ApplicationValidator applicationValidator;
    DatabaseConnector databaseConnector;

    public ApplicationService(final ApplicationDao applicationDao,
                              final ApplicationValidator applicationValidator,
                              final DatabaseConnector databaseConnector) {
        this.applicationDao = applicationDao;
        this.applicationValidator = applicationValidator;
        this.databaseConnector = databaseConnector;
    }

    public void createApplication(
            final ApplicationRequest applicationRequest)
            throws SQLException, InvalidException,
            DatabaseConnectionException {
        applicationValidator.validateApplication(applicationRequest);

        applicationDao.createApplication(applicationRequest,
                databaseConnector.getConnection());

    }
}
