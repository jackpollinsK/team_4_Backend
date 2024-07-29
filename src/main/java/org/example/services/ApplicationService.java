package org.example.services;

import org.example.daos.ApplicationDao;
import org.example.daos.DatabaseConnector;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.Entity;
import org.example.exceptions.FailedToCreateException;
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

    public int createDeliveryEmployee(
            final ApplicationRequest applicationRequest)
            throws FailedToCreateException, SQLException, InvalidException,
            DatabaseConnectionException {
        applicationValidator.validateApplication(applicationRequest);

        int id = applicationDao.createApplication(applicationRequest,
                databaseConnector.getConnection());

        if (id == -1) {
            throw new FailedToCreateException(Entity.APPLICATION);
        }

        return id;
    }
}
