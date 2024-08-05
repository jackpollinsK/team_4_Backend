package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LocationDao {

    public List<Location> getAllLocations(final Connection c)
            throws SQLException, DatabaseConnectionException {
        List<Location> locations = new ArrayList<>();
        String query = "SELECT name, address, phone FROM location;";

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Location location =
                        new Location(
                                resultSet.getString("name"),
                                resultSet.getString("address"),
                                resultSet.getString("phone"));
                locations.add(location);
            }
        }

        return locations;
    }
}
