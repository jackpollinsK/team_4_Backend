package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Capability;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CapabilityDao {

    public List<Capability> getAllCapabilities(final Connection c)
            throws SQLException, DatabaseConnectionException {
        List<Capability> capabilities = new ArrayList<>();
        String query = "SELECT name FROM capability;";

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Capability capability =
                        new Capability(resultSet.getString("name"));
                capabilities.add(capability);
            }
        }

        return capabilities;
    }
}
