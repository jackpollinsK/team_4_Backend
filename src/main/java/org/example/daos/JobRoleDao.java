package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.FilterRequest;
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

    public List<JobRole> filterJobRoles(final String location,
                                        final String band,
                                        final String capability,
                                        final Connection c)
            throws SQLException, InvalidException {

        List<JobRole> jobRoles = new ArrayList<>();

        String query =
                "SELECT jr.id, role_name, l.name, c.name, b.name, "
                        + "closing_date, status "
                        + "FROM jobRoles jr "
                        + "INNER JOIN location l "
                        + "ON l.id = jr.location_id "
                        + "INNER JOIN capability c "
                        + "ON c.id = jr.capability_id "
                        + "INNER JOIN band b "
                        + "ON b.id = jr.band_id "
                        + "WHERE l.name = ? and role_name in ( "
                        + "SELECT role_name  "
                        + "FROM jobRoles jr  "
                        + "INNER JOIN capability c "
                        + "ON c.id = jr.capability_id "
                        + "WHERE c.name = ? and role_name in ( "
                        + "SELECT role_name "
                        + "FROM jobRoles jr "
                        + "INNER JOIN band b "
                        + "ON b.id = jr.band_id "
                        + "where b.name = ?)); ";


        try (PreparedStatement statement = c.prepareStatement(query)) {

            statement.setString(1, location);
            statement.setString(2, capability);
            statement.setString(3, band);

            ResultSet resultSet = statement.executeQuery();

            int count = 0;

            while (resultSet.next()) {
                JobRole jobRole = new JobRole(
                        resultSet.getInt("id"),
                        resultSet.getString("role_name"),
                        resultSet.getString("l.name"),
                        resultSet.getString("c.name"),
                        resultSet.getString("b.name"),
                        resultSet.getDate("closing_date"),
                        "open"
                );
                jobRoles.add(jobRole);
                count++;
            }

            if (count == 0) {
                getUnionedJobRoles(location, band, capability, c);
            }


        }

        return jobRoles;
    }

    private List<JobRole> getUnionedJobRoles(final String location,
                                             final String band,
                                             final String capability,
                                             final Connection c)
            throws SQLException, InvalidException {

        List<JobRole> jobRoles = new ArrayList<>();

        String query =
                "SELECT jr.id, role_name, l.name, c.name, b.name, "
                        + "closing_date, status "
                        + "FROM jobRoles jr  "
                        + "INNER JOIN location l "
                        + "ON l.id = jr.location_id "
                        + "INNER JOIN capability c "
                        + "ON c.id = jr.capability_id "
                        + "INNER JOIN band b "
                        + "ON b.id = jr.band_id "
                        + "where l.name = ? and status = 'open' "
                        + "UNION "
                        + "SELECT jr.id, role_name,l.name,c.name,b.name, "
                        + "closing_date, status "
                        + "FROM jobRoles jr  "
                        + "INNER JOIN location l "
                        + "ON l.id = jr.location_id "
                        + "INNER JOIN capability c "
                        + "ON c.id = jr.capability_id "
                        + "INNER JOIN band b "
                        + "ON b.id = jr.band_id "
                        + "where c.name = ? and status = 'open' "
                        + "UNION "
                        + "SELECT jr.id, role_name, l.name, c.name, b.name, "
                        + "closing_date, status "
                        + "FROM jobRoles jr  "
                        + "INNER JOIN location l "
                        + "ON l.id = jr.location_id "
                        + "INNER JOIN capability c "
                        + "ON c.id = jr.capability_id "
                        + "INNER JOIN band b "
                        + "ON b.id = jr.band_id "
                        + "where b.name = ? and status = 'open';";

        try (PreparedStatement statement = c.prepareStatement(query)) {
            statement.setString(1, location);
            statement.setString(2, capability);
            statement.setString(3, band);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                JobRole jobRole = new JobRole(
                        resultSet.getInt("id"),
                        resultSet.getString("role_name"),
                        resultSet.getString("l.name"),
                        resultSet.getString("c.name"),
                        resultSet.getString("b.name"),
                        resultSet.getDate("closing_date"),
                        "open"
                );
                jobRoles.add(jobRole);
            }
        }


        return jobRoles;

    }
}
