package org.example.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.JobRole;
import org.example.models.UserRole;
import org.example.exceptions.DoesNotExistException;
import org.example.models.JobRoleInfo;
import org.example.services.JobRoleService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Job Roles API")
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

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.USER, UserRole.ADMIN})
    @ApiOperation(
            value = "Returns Job Role By Id",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = JobRole.class)
    public Response getJobRoleById(final @PathParam("id") int id) {
        try {
            JobRoleInfo jobRoleInfo = jobRoleService.getJobRoleById(id);
            return Response.ok().entity(jobRoleInfo).build();
        } catch (SQLException | DatabaseConnectionException e) {
            return Response.serverError().build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRole.ADMIN})
    @ApiOperation(
            value = "Deletes Job Role By Id",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = JobRole.class)
    public Response deleteJobRole(final @PathParam("id") int id) {
        try {
            jobRoleService.deleteJobRole(id);
            return Response.noContent().build();
        } catch (SQLException | DatabaseConnectionException e) {
            return Response.serverError().entity(e.getMessage()).build();
        } catch (DoesNotExistException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        }
    }
}
