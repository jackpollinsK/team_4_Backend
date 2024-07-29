package org.example.services;

import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
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
            DatabaseConnectionException {
        return jobRoleDao.getJobRoleById(id, databaseConnector.getConnection());
    }
}
