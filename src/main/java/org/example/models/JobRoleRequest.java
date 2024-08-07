package org.example.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Date;


public class JobRoleRequest {

    private String roleName;
    private int location;
    private int capability;
    private int band;
    private Date closingDate;
    private String description;
    private String responsibilities;
    private String jobSpec;
    private int openPositions;
    private String link;

    @JsonCreator
    public JobRoleRequest(@JsonProperty("roleName") final String roleName,
                          @JsonProperty("location") final int location,
                          @JsonProperty("capability") final int capability,
                          @JsonProperty("band") final int band,
                          @JsonProperty("closingDate") final Date closingDate,
                          @JsonProperty("description") final String description,
                          @JsonProperty("responsibilities")
                          final String responsibilities,
                          @JsonProperty("jobSpec") final String jobSpec,
                          @JsonProperty("openPositions")
                          final int openPositions,
                          @JsonProperty("link") final String link) {
        this.roleName = roleName;
        this.location = location;
        this.capability = capability;
        this.band = band;
        this.closingDate = closingDate;
        this.description = description;
        this.responsibilities = responsibilities;
        this.jobSpec = jobSpec;
        this.openPositions = openPositions;
        this.link = link;
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

    public int getOpenPositions() {
        return openPositions;
    }

    public String getLink() {
        return link;
    }

    public void setOpenPositions(final int openPositions) {
        this.openPositions = openPositions;
    }

    public void setLink(final String link) {
        this.link = link;
    }
}
