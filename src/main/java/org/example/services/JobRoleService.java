package org.example.services;

import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;
import java.sql.SQLException;
import java.util.List;

public class JobRoleService {
    private final JobRoleDao jobRoleDao;
    private final DatabaseConnector databaseConnector;

    public JobRoleService(final JobRoleDao jobRoleDao,
                          final DatabaseConnector databaseConnector) {
        this.jobRoleDao = jobRoleDao;
        this.databaseConnector = databaseConnector;
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
