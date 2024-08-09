package org.example.validators;

import org.example.exceptions.Entity;
import org.example.exceptions.InvalidException;
import org.example.models.JobRoleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JobRoleValidatorTest {

    private JobRoleValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new JobRoleValidator();
    }

    @Test
    public void testValidJobRoleRequest() throws InvalidException {
        JobRoleRequest request = new JobRoleRequest("Valid Role", 1, 1, 1, new Date(System.currentTimeMillis()), "Valid Description", "Valid Responsibilities", "Valid Job Spec", 10);

        // This should not throw any exception
        validator.validateJobRole(request);
    }

    @Test
    public void testRoleNameTooLong() {
        JobRoleRequest request = new JobRoleRequest("R".repeat(101), 1, 1, 1, new Date(System.currentTimeMillis()), "Valid Description", "Valid Responsibilities", "Valid Job Spec", 10);

        InvalidException thrown = assertThrows(InvalidException.class, () -> validator.validateJobRole(request));
        assertEquals(Entity.JOB_ROLE.getEntity() + " is not valid: Role name must be less than 100 Characters", thrown.getMessage());
    }

    @Test
    public void testRoleNameNull() {
        JobRoleRequest request = new JobRoleRequest(null, 1, 1, 1, new Date(System.currentTimeMillis()), "Valid Description", "Valid Responsibilities", "Valid Job Spec", 10);

        InvalidException thrown = assertThrows(InvalidException.class, () -> validator.validateJobRole(request));
        assertEquals(Entity.JOB_ROLE.getEntity() + " is not valid: Role name must be not be null", thrown.getMessage());
    }

    @Test
    public void testDescriptionTooLong() {
        JobRoleRequest request = new JobRoleRequest("Valid Role", 1, 1, 1, new Date(System.currentTimeMillis()), "D".repeat(2001), "Valid Responsibilities", "Valid Job Spec", 10);

        InvalidException thrown = assertThrows(InvalidException.class, () -> validator.validateJobRole(request));
        assertEquals(Entity.JOB_ROLE.getEntity() + " is not valid: Description name must be less than 2000 Characters", thrown.getMessage());
    }

    @Test
    public void testDescriptionNull() {
        JobRoleRequest request = new JobRoleRequest("Valid Role", 1, 1, 1, new Date(System.currentTimeMillis()), null, "Valid Responsibilities", "Valid Job Spec", 10);

        InvalidException thrown = assertThrows(InvalidException.class, () -> validator.validateJobRole(request));
        assertEquals(Entity.JOB_ROLE.getEntity() + " is not valid: Description name must be not be null", thrown.getMessage());
    }

    @Test
    public void testResponsibilitiesTooLong() {
        JobRoleRequest request = new JobRoleRequest("Valid Role", 1, 1, 1, new Date(System.currentTimeMillis()), "Valid Description", "R".repeat(1001), "Valid Job Spec", 10);

        InvalidException thrown = assertThrows(InvalidException.class, () -> validator.validateJobRole(request));
        assertEquals(Entity.JOB_ROLE.getEntity() + " is not valid: Responsibility name must be less than 1000 Characters", thrown.getMessage());
    }

    @Test
    public void testResponsibilitiesNull() {
        JobRoleRequest request = new JobRoleRequest("Valid Role", 1, 1, 1, new Date(System.currentTimeMillis()), "Valid Description", null, "Valid Job Spec", 10);

        InvalidException thrown = assertThrows(InvalidException.class, () -> validator.validateJobRole(request));
        assertEquals(Entity.JOB_ROLE.getEntity() + " is not valid: Responsibility name must be not be null", thrown.getMessage());
    }

    @Test
    public void testJobSpecTooLong() {
        JobRoleRequest request = new JobRoleRequest("Valid Role", 1, 1, 1, new Date(System.currentTimeMillis()), "Valid Description", "Valid Responsibilities", "J".repeat(501), 10);

        InvalidException thrown = assertThrows(InvalidException.class, () -> validator.validateJobRole(request));
        assertEquals(Entity.JOB_ROLE.getEntity() + " is not valid: Job Spec name must be less than 500 Characters", thrown.getMessage());
    }
}
