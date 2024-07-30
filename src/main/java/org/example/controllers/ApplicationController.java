package org.example.controllers;


import io.swagger.annotations.Api;
import org.example.exceptions.DatabaseConnectionException;
import org.example.exceptions.InvalidException;
import org.example.models.ApplicationRequest;
import org.example.services.ApplicationService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    @Path("/apply-for-role")
    public Response apply(final ApplicationRequest applicationRequest) {
        try {
            applicationService.createApplication(
                    applicationRequest);
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
}
