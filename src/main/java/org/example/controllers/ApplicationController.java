package org.example.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.eclipse.jetty.http.HttpStatus;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.InvalidException;
import org.example.models.Application;
import org.example.models.ApplicationRequest;
import org.example.models.UserRole;
import org.example.services.ApplicationService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Application API")
@Path("/api")
public class ApplicationController {

    ApplicationService applicationService;

    public ApplicationController(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @POST
    @Path("/applyForJobRole")
    @RolesAllowed(UserRole.USER)
    @ApiOperation(
            value = "Inserts an application",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Integer.class)
    public Response apply(final ApplicationRequest applicationRequest) {
        try {
            applicationService.createApplication(applicationRequest);
            return Response
                    .status(Response.Status.CREATED)
                    .build();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return Response.serverError().build();
        } catch (InvalidException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage()).build();
        } catch (DatabaseConnectionException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/getAppliedJobs")
    @RolesAllowed(UserRole.USER)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Gets all applications from a user",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Application.class)
    public Response getAppliedJobs(final String email) {

        try {
            return Response
                    .status(HttpStatus.OK_200)
                    .entity(applicationService.getAllApplications(email))
                    .build();
        } catch (DatabaseConnectionException | SQLException e) {
            return Response.serverError().build();
        }

    }
}
