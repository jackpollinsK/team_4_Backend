package org.example.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class JobRoleInfo {
    private int id;
    private String roleName;
    private String location;
    private String capability;
    private String band;
    private Date closingDate;
    private String status;
    private String description;
    private String responsibilities;
    private String jobSpec;

    @JsonCreator
    public JobRoleInfo(@JsonProperty("id") final int id,
                       @JsonProperty("roleName") final String roleName,
                       @JsonProperty("location") final String location,
                       @JsonProperty("capability") final String capability,
                       @JsonProperty("band") final String band,
                       @JsonProperty("closingDate") final Date closingDate,
                       @JsonProperty("status") final String status,
                       @JsonProperty("description") final String description,
                       @JsonProperty("responsibilities")
                           final String responsibilities,
                       @JsonProperty("jobSpec") final String jobSpec) {
        this.id = id;
        this.roleName = roleName;
        this.location = location;
        this.capability = capability;
        this.band = band;
        this.closingDate = closingDate;
        this.status = status;
        this.description = description;
        this.responsibilities = responsibilities;
        this.jobSpec = jobSpec;
    }

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(final Date closingDate) {
        this.closingDate = closingDate;
    }

    public String getBand() {
        return band;
    }

    public void setBand(final String band) {
        this.band = band;
    }

    public String getCapability() {
        return capability;
    }

    public void setCapability(final String capability) {
        this.capability = capability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(final String roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(final String responsibilities) {
        this.responsibilities = responsibilities;
    }

    public String getJobSpec() {
        return jobSpec;
    }

    public void setJobSpec(final String jobSpec) {
        this.jobSpec = jobSpec;
    }
}
