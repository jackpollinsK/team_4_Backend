package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.models.JobRole;
import org.example.models.JobRoleRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobRoleDao {
    public List<JobRole> getAllJobRoles(final Connection c)
            throws SQLException, DatabaseConnectionException {
        List<JobRole> jobRoles = new ArrayList<>();

        Statement statement = c.createStatement();

        ResultSet resultSet = statement.executeQuery(
                "SELECT " + "jr.id, " + "jr.role_name AS `RoleName`, "
                        + "l.name AS `Location`, " + "c.name AS `Capability`, "
                        + "b.name AS `Band`, "
                        + "jr.closing_date AS `ClosingDate` "
                        + "FROM jobRoles jr "
                        + "INNER JOIN location l ON jr.location_id = l.id "
                        + "INNER JOIN capability c "
                        + "ON jr.capability_id = c.id "
                        + "INNER JOIN band b ON jr.band_id = b.id "
                        + "WHERE jr.status = 'open';");

        while (resultSet.next()) {
            JobRole jobRole = new JobRole(resultSet.getInt("id"),
                    resultSet.getString("RoleName"),
                    resultSet.getString("Location"),
                    resultSet.getString("Capability"),
                    resultSet.getString("Band"),
                    resultSet.getDate("ClosingDate"), "open");

            jobRoles.add(jobRole);
        }

        return jobRoles;
    }

    public int insertRole(final JobRoleRequest jobRoleRequest,
                          final Connection c) throws SQLException {
        String insertRoleQuery =
                "INSERT INTO jobRoles(role_name, location_id, capability_id, "
                        + "band_id, closing_date, status) VALUES "
                        + "('Test', 1, 1, 1, '2000-01-01', 'open');";

        PreparedStatement preparedStmt = c.prepareStatement(insertRoleQuery,
                Statement.RETURN_GENERATED_KEYS);
        preparedStmt.setString(1, jobRoleRequest.getRoleName());
        preparedStmt.setInt(2, jobRoleRequest.getLocation());
        preparedStmt.setInt(3, jobRoleRequest.getCapability());
        preparedStmt.setInt(4, jobRoleRequest.getBand());
        preparedStmt.setString(5, jobRoleRequest.getClosingDate().toString());

        int affectedRows = preparedStmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }

        return -1;
    }

}
