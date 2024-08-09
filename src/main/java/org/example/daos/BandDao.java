
package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Band;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BandDao {

    public List<Band> getAllBands(final Connection c)
            throws SQLException, DatabaseConnectionException {
        List<Band> bands = new ArrayList<>();
        String query = "SELECT id, name FROM band;";

        try (PreparedStatement statement = c.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Band band = new Band(resultSet.getInt("id"),
                        resultSet.getString("name"));
                bands.add(band);
            }
        }

        return bands;
    }
}
