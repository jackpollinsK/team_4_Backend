package org.example.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.eclipse.jetty.http.HttpStatus;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.InvalidException;
import org.example.models.JobRole;
import org.example.models.JobRoleRequest;
import org.example.models.UserRole;
import org.example.services.JobRoleService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Job Roles")
@Path("/api/JobRoles")
public class JobRoleController {

    final JobRoleService jobRoleService;

    public JobRoleController(final JobRoleService jobRoleService) {
        this.jobRoleService = jobRoleService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    @ApiOperation(
            value = "Returns Job Roles",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = JobRole.class)
    public Response getJobRoles() {
        try {
            return Response.ok().entity(jobRoleService.getJobRoles()).build();
        } catch (SQLException | DatabaseConnectionException e) {
            return Response.serverError().build();
        }
    }

    @POST
    public Response addRole(final JobRoleRequest jobRoleRequest) {
        try {
            int id = jobRoleService.insertRole(jobRoleRequest);
            return Response.status(HttpStatus.CREATED_201).entity(id).build();
        } catch (DatabaseConnectionException | SQLException e) {
            return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
                    .entity(e.getMessage()).build();
        } catch (InvalidException e) {
            return Response.status(HttpStatus.BAD_REQUEST_400)
                    .entity(e.getMessage()).build();
        }

    }
}
