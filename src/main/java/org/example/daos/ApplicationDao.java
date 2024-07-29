package org.example.daos;

import org.example.models.ApplicationRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.SQLException;


public class ApplicationDao {

    public void createApplication(
            final ApplicationRequest applicationRequest, final Connection c)
            throws SQLException {

        String insertStatement =
                "INSERT INTO roleApplication VALUES "
                        + "(?, ?, ?);";

        PreparedStatement st = c.prepareStatement(insertStatement);

        st.setString(1, applicationRequest.getEmail());
        st.setInt(2, applicationRequest.getRoleID());
        st.setString(3, applicationRequest.getCvLink());

        st.executeUpdate();
    }
}
