package org.example.services;

import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;
import org.example.models.JobRoleRequest;
import org.example.validators.JobRoleValidator;

import java.sql.SQLException;
import java.util.List;

public class JobRoleService {
    private final JobRoleDao jobRoleDao;
    private final DatabaseConnector databaseConnector;
    private final JobRoleValidator jobRoleValidator;

    public JobRoleService(final JobRoleDao jobRoleDao,
                          final DatabaseConnector databaseConnector,
                          final JobRoleValidator jobRoleValidator) {
        this.jobRoleDao = jobRoleDao;
        this.databaseConnector = databaseConnector;
        this.jobRoleValidator = jobRoleValidator;
    }

    public int insertRole(final JobRoleRequest jobRoleRequest)
            throws DatabaseConnectionException, SQLException, InvalidException {
        jobRoleValidator.validateJobRole(jobRoleRequest);
        int id = jobRoleDao.insertRole(jobRoleRequest,
                databaseConnector.getConnection());

        if (id != -1) {
            return id;
        } else {
            throw new InvalidException(Entity.JOB_ROLE, "Invalid Data");
        }
    }

    public List<JobRole> getJobRoles() throws SQLException,
            DatabaseConnectionException {
        return jobRoleDao.getAllJobRoles(databaseConnector.getConnection());
    }

    public JobRoleInfo getJobRoleById(final int id) throws SQLException,
            DatabaseConnectionException, DoesNotExistException {
        JobRoleInfo jobRoleInfo = jobRoleDao.getJobRoleById(id,
                databaseConnector.getConnection());
        if (jobRoleInfo == null) {
            throw new DoesNotExistException(Entity.JOB_ROLE);
        }
            return jobRoleInfo;
    }

    public void deleteJobRole(final int id)
          throws  SQLException, DoesNotExistException,
            DatabaseConnectionException {
        JobRoleInfo jobRoleToUpdate = jobRoleDao.getJobRoleById(id,
                databaseConnector.getConnection());

        if (jobRoleToUpdate == null) {
            throw new DoesNotExistException(Entity.JOB_ROLE);
        }
        jobRoleDao.deleteJobRole(id, databaseConnector.getConnection());

    }
}
