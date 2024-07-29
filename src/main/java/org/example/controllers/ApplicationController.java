package org.example.controllers;


import io.swagger.annotations.Api;
import org.example.models.LoginRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Api("Application API")
@Path("/api")
public class ApplicationController {

    @POST
    @Path("/apply-for-role")
    public Response login(final LoginRequest loginRequest) {
        return null;
    }
}
