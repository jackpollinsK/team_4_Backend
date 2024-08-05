package org.example.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.JobRole;
import org.example.models.UserRole;
import org.example.services.CapabilityService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Capability API")
@Path("/api/capability")
public class CapabilityController {

    final CapabilityService capabilityService;

    public CapabilityController(final CapabilityService capabilityService) {
        this.capabilityService = capabilityService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(
            value = "Returns Capabilities",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = JobRole.class)
    public Response getCapabilities() {
        try {
            return Response.ok().entity(capabilityService.getCapabilities())
                    .build();
        } catch (SQLException | DatabaseConnectionException e) {
            return Response.serverError().build();
        }
    }
}
