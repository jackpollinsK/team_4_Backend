package org.example.daos;

import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.DoesNotExistException;
import org.example.exceptions.Entity;
import org.example.models.LoginRequest;
import org.example.models.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDao {

    public User getUser(final LoginRequest loginRequest, final Connection c)
            throws SQLException, DatabaseConnectionException,
            DoesNotExistException {
            String query = "SELECT * FROM `User` "
                  +  "WHERE Email = ?";
            PreparedStatement statement = c.prepareStatement(query);

            statement.setString(1, loginRequest.getEmail());

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (BCrypt.checkpw(loginRequest.getPassword(),
                        resultSet.getString("Hash"))) {
                    return new User(
                            resultSet.getString("Email"),
                            resultSet.getString("Salt"),
                            resultSet.getString("Hash"),
                            resultSet.getInt("RoleId"));
                } else {
                    return null;  //user found but wrong password
                }
            }
        throw new DoesNotExistException(Entity.USER); //user not found
    }

    public void generateUsers(final Connection c) throws SQLException,
            DatabaseConnectionException {

        String insertStatement =
                "INSERT INTO `User` (Email, Salt, Hash, RoleId) "
                        + "VALUES (?,?,?,?)";

        PreparedStatement st = c.prepareStatement(insertStatement);

        String email1 = "eoghan@random.com";
        String salt1 = BCrypt.gensalt();
        String password1 = "password321";
        String hashedAndSaltedPassword1 = BCrypt.hashpw(password1, salt1);
        int roleId1 = 1;

        st.setString(1, email1);
        st.setString(2, salt1);
        st.setString(3, hashedAndSaltedPassword1);
        st.setInt(4, roleId1);

        st.executeUpdate();

        st = c.prepareStatement(insertStatement);

        String email2 = "adam@random.com";
        String salt2 = BCrypt.gensalt();
        String password2 = "pass123";
        String hashedAndSaltedPassword2 = BCrypt.hashpw(password2, salt2);
        int roleId2 = 2;

        st.setString(1, email2);
        st.setString(2, salt2);
        st.setString(3, hashedAndSaltedPassword2);
        st.setInt(4, roleId2);

        st.executeUpdate();
    }
}
