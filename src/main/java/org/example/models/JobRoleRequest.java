package org.example.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class JobRoleRequest {

    private String roleName;
    private int location;
    private int capability;
    private int band;
    private Date closingDate;

    @JsonCreator
    public JobRoleRequest(@JsonProperty("roleName") final String roleName,
                          @JsonProperty("location") final int location,
                          @JsonProperty("capability") final int capability,
                          @JsonProperty("band") final int band,
                          @JsonProperty("closingDate") final Date closingDate) {
        this.roleName = roleName;
        this.location = location;
        this.capability = capability;
        this.band = band;
        this.closingDate = closingDate;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(final int location) {
        this.location = location;
    }

    public int getCapability() {
        return capability;
    }

    public void setCapability(final int capability) {
        this.capability = capability;
    }

    public int getBand() {
        return band;
    }

    public void setBand(final int band) {
        this.band = band;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(final Date closingDate) {
        this.closingDate = closingDate;
    }
}
