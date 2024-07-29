package org.example.validators;

import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;

public class ApplicationValidator {

    public void validateApplication(
            final ApplicationRequest applicationRequest)
            throws InvalidException {

        if (applicationRequest.getEmail() == null) {
            throw new InvalidException(Entity.APPLICATION,
                    "You must enter an Email");
        }

        if (applicationRequest.getRoleID() == 0) {
            throw new InvalidException(Entity.APPLICATION,
                    "Role cannot be left blank");
        }

        if (applicationRequest.getCvLink() == null) {
            throw new InvalidException(Entity.APPLICATION,
                    "CV cannot be left blank");
        }

    }
}
