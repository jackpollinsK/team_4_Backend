package org.example.validators;

import org.example.daos.JobRoleDao;
import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.JobRoleRequest;

public class JobRoleValidator {

    JobRoleDao jobRoleDao;

    public void validateJobRole(final JobRoleRequest jobRoleRequest) throws
            InvalidException {
        if (jobRoleRequest.getRoleName().length() > 100) {
            throw new InvalidException(Entity.JOBROLES,
                    "Role name must be less than 100 Characters");
        }
        if (jobRoleRequest.getRoleName() == null) {
            throw new InvalidException(Entity.JOBROLES,
                    "Role name must be not be null");
        }
        if (jobRoleRequest.getDescription().length() < 2000) {
            throw new InvalidException(Entity.JOBROLES,
                    "Description name must be less than 2000 Characters");
        }
        if (jobRoleRequest.getDescription() == null) {
            throw new InvalidException(Entity.JOBROLES,
                    "Description name must be not be null");
        }
        if (jobRoleRequest.getResponsibilities().length() < 1000) {
            throw new InvalidException(Entity.JOBROLES,
                    "Responsibility name must be less than 1000 Characters");
        }
        if (jobRoleRequest.getResponsibilities() == null) {
            throw new InvalidException(Entity.JOBROLES,
                    "Responsibility name must be not be null");
        }
    }
}