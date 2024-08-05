package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationRequest {

    @JsonProperty
    String email;

    @JsonProperty
    int roleID;

    @JsonProperty
    String cvLink;

    public ApplicationRequest(
            @JsonProperty("email") final String email,
            @JsonProperty("roleID") final int roleID,
            @JsonProperty("cvLink") final String cvLink) {
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
