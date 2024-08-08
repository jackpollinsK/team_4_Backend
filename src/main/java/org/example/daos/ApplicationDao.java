package org.example.daos;

import org.example.models.ApplicationRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ApplicationDao {

    public void createApplication(
            final ApplicationRequest applicationRequest, final Connection c)
            throws SQLException {

        String insertStatement =
                "INSERT INTO roleApplication VALUES "
                        + "(?, ?, ?, ?);";

        PreparedStatement st = c.prepareStatement(insertStatement);

        st.setString(1, applicationRequest.getEmail());
        st.setInt(2, applicationRequest.getRoleID());
        st.setString(3, applicationRequest.getCvLink());
        st.setBoolean(4, true);

        st.executeUpdate();
    }

    public boolean alreadyApplied(final ApplicationRequest applicationRequest,
                                  final Connection c) throws SQLException {
        boolean applied = false;
        String insertStatement =
                "SELECT * FROM roleApplication WHERE Email = ? AND role_id = ?";

        PreparedStatement st = c.prepareStatement(insertStatement);

        st.setString(1, applicationRequest.getEmail());
        st.setInt(2, applicationRequest.getRoleID());

        st.executeQuery();

        ResultSet resultSet = st.executeQuery();

        return resultSet.next();


    }


    public void deleteApplication(
            final ApplicationRequest applicationRequest,
            final Connection c) throws
            SQLException {
        String deleteStatement =
                "DELETE FROM roleApplication WHERE Email = ? AND role_id = ?";
        PreparedStatement st = c.prepareStatement(deleteStatement);
        st.setString(1, applicationRequest.getEmail());
        st.setInt(2, applicationRequest.getRoleID());
        st.executeUpdate();
    }
}


