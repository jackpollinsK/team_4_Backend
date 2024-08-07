package org.example.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FilterRequest {

    @JsonProperty
    String location;

    @JsonProperty
    String band;

    @JsonProperty
    String capability;


    public FilterRequest(@JsonProperty("location") final String location,
                         @JsonProperty("band") final String band,
                         @JsonProperty("capability") final String capability) {
        this.location = location;
        this.band = band;
        this.capability = capability;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(final String location) {
        this.location = location;
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
}
