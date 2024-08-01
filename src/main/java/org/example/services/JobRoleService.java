package org.example.services;

import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.JobRole;
import org.example.models.JobRoleRequest;

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

    public int insertRole(final JobRoleRequest jobRoleRequest)
            throws DatabaseConnectionException, SQLException, InvalidException {

        int id = jobRoleDao.insertRole(jobRoleRequest,
                databaseConnector.getConnection());

        if(id != -1)
            return id;
        else
            throw new InvalidException(Entity.JOBROLES, "Invalid Data");
    }

    public List<JobRole> getJobRoles() throws SQLException,
            DatabaseConnectionException {
        return jobRoleDao.getAllJobRoles(databaseConnector.getConnection());
    }
}
