package org.example.models;

public class Application {

    String email;
    int roleID;
    String cvLink;

    public Application(final String email, final int roleID,
                       final String cvLink) {
        this.email = email;
        this.roleID = roleID;
        this.cvLink = cvLink;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(final int roleID) {
        this.roleID = roleID;
    }

    public String getCvLink() {
        return cvLink;
    }

    public void setCvLink(final String cvLink) {
        this.cvLink = cvLink;
    }
}
