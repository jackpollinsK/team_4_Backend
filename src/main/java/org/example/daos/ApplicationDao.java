package org.example.daos;

import org.example.models.Application;
import org.example.models.ApplicationRequest;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


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

    public List<Application> getAllApplications(
            final String email,
            final Connection c) throws SQLException {
        List<Application> applications = new ArrayList<>();
        String query =
                "SELECT Email, role_id, cv_link FROM "
                        + "roleApplication WHERE Email = ?";

        try (PreparedStatement st = c.prepareStatement(query)) {
            st.setString(1, email);
            try (ResultSet resultSet = st.executeQuery()) {

                while (resultSet.next()) {
                    Application application = new Application(
                            resultSet.getString("Email"),
                            resultSet.getInt("role_id"),
                            resultSet.getString("cv_link")
                    );
                    applications.add(application);
                }
            }

            return applications;


        }
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


