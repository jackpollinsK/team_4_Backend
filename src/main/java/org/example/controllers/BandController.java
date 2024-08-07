package org.example.controllers;


import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Band;
import org.example.models.UserRole;
import org.example.services.BandService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Band API")
@Path("/api/band")
public class BandController {

    BandService bandService;

    public BandController(final BandService bandService) {
        this.bandService = bandService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(UserRole.ADMIN)
    @ApiOperation(
            value = "Returns Bands",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Band.class)
    public Response getBands() {
        try {
            return Response.ok().entity(bandService.getBands()).build();
        } catch (SQLException | DatabaseConnectionException e) {
            return Response.serverError().build();
        }
    }
}
