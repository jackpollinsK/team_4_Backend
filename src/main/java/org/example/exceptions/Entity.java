package org.example.exceptions;

public enum Entity {
    JOBROLES("Job Roles"),
    USER("User");

    private final String entity;

    Entity(final String entity) {
        this.entity = entity;
    }

    public String getEntity() {
        return this.entity;
    }
}
