package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.models.ApplicationRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ApplicationDao {

    public int createApplication(
            final ApplicationRequest applicationRequest, final Connection c)
            throws SQLException {

        String insertStatement =
                "INSERT INTO roleApplication VALUES "
                        + "(?, ?, ?);";

        PreparedStatement st = c.prepareStatement(insertStatement,
                Statement.RETURN_GENERATED_KEYS);

        st.setString(1, applicationRequest.getEmail());
        st.setInt(2, applicationRequest.getRoleID());
        st.setString(3, applicationRequest.getCvLink());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }
}
