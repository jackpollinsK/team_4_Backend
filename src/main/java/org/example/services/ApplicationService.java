package org.example.services;

import org.example.daos.ApplicationDao;
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

    public ApplicationService(final ApplicationDao applicationDao,
                              final ApplicationValidator applicationValidator) {
        this.applicationDao = applicationDao;
        this.applicationValidator = applicationValidator;
    }

    public int createDeliveryEmployee(
            final ApplicationRequest applicationRequest)
            throws FailedToCreateException, SQLException, InvalidException,
            DatabaseConnectionException {
        applicationValidator.validateApplication(applicationRequest);

        int id = applicationDao.createApplication(applicationRequest);

        if (id == -1) {
            throw new FailedToCreateException(Entity.APPLICATION);
        }

        return id;
    }
}
