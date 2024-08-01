package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.models.JobRole;
import org.example.models.JobRoleInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {

    public List<JobRole> getAllJobRoles(final Connection c)
            throws SQLException, DatabaseConnectionException {
        List<JobRole> jobRoles = new ArrayList<>();
        String query = "SELECT "
                + "jr.id, jr.role_name AS RoleName, "
                + "l.name AS Location, "
                + "c.name AS Capability, "
                + "b.name AS Band, "
                + "jr.closing_date AS ClosingDate "
                + "FROM jobRoles jr "
                + "INNER JOIN location l ON jr.location_id = l.id "
                + "INNER JOIN capability c ON jr.capability_id = c.id "
                + "INNER JOIN band b ON jr.band_id = b.id "
                + "WHERE jr.status = 'open';";

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                JobRole jobRole = new JobRole(
                        resultSet.getInt("id"),
                        resultSet.getString("RoleName"),
                        resultSet.getString("Location"),
                        resultSet.getString("Capability"),
                        resultSet.getString("Band"),
                        resultSet.getDate("ClosingDate"),
                        "open"
                );
                jobRoles.add(jobRole);
            }
        }
        return jobRoles;
    }

    public JobRoleInfo getJobRoleById(final int id, final Connection c)
            throws SQLException, DatabaseConnectionException {
        String query = "SELECT "
                + "jr.id, jr.role_name AS RoleName, "
                + "l.name AS Location, "
                + "c.name AS Capability, "
                + "b.name AS Band, "
                + "jr.closing_date AS ClosingDate, "
                + "jr.description AS Description, "
                + "jr.responsibilities AS Responsibilities, "
                + "jr.job_spec AS JobSpec "
                + "FROM jobRoles jr "
                + "INNER JOIN location l ON jr.location_id = l.id "
                + "INNER JOIN capability c ON jr.capability_id = c.id "
                + "INNER JOIN band b ON jr.band_id = b.id "
                + "WHERE jr.status = 'open' AND jr.id = ?;";

        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new JobRoleInfo(
                            resultSet.getInt("id"),
                            resultSet.getString("RoleName"),
                            resultSet.getString("Location"),
                            resultSet.getString("Capability"),
                            resultSet.getString("Band"),
                            resultSet.getDate("ClosingDate"),
                            "open",
                            resultSet.getString("Description"),
                            resultSet.getString("Responsibilities"),
                            resultSet.getString("JobSpec")
                    );
                }
            }
        }
        return null;
    }
}
