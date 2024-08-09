package org.example.controllers;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.example.exceptions.DatabaseConnectionException;
import org.example.models.Location;
import org.example.models.UserRole;
import org.example.services.LocationService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

@Api("Location API")
@Path("/api/location")
public class LocationController {

    final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed(UserRole.ADMIN)
    @ApiOperation(
            value = "Returns Locations",
            authorizations = @Authorization(value = HttpHeaders.AUTHORIZATION),
            response = Location.class)
    public Response getLocations() {
        try {
            return Response.ok().entity(locationService.getLocations()).build();
        } catch (SQLException | DatabaseConnectionException e) {
            return Response.serverError().build();
        }
    }
}
