package org.example;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import io.jsonwebtoken.Jwts;

import org.example.auth.JwtAuthenticator;
import org.example.auth.RoleAuthoriser;
import org.example.controllers.ApplicationController;
import org.example.controllers.AuthController;
import org.example.controllers.BandController;
import org.example.controllers.CapabilityController;
import org.example.controllers.JobRoleController;
import org.example.controllers.LocationController;
import org.example.daos.ApplicationDao;
import org.example.daos.AuthDao;
import org.example.daos.BandDao;
import org.example.daos.CapabilityDao;
import org.example.daos.DatabaseConnector;
import org.example.daos.JobRoleDao;
import org.example.daos.LocationDao;
import org.example.models.JwtToken;
import org.example.services.ApplicationService;
import org.example.services.AuthService;
import org.example.services.BandService;
import org.example.services.CapabilityService;
import org.example.services.JobRoleService;
import org.example.services.LocationService;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import java.security.Key;

public class JDDApplication extends Application<JDDConfiguration> {
    public static void main(final String[] args) throws Exception {
        new JDDApplication().run(args);
    }

    @Override
    public String getName() {
        return "JDD";
    }

    @Override
    public void initialize(final Bootstrap<JDDConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(
                    final JDDConfiguration configuration) {
                return configuration.getSwagger();
            }
        });
    }

    @Override
    public void run(final JDDConfiguration configuration,
                    final Environment environment) {
        Key jwtKey = Jwts.SIG.HS256.key().build();
        DatabaseConnector databaseConnector = new DatabaseConnector();

        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<JwtToken>()
                        .setAuthenticator(new JwtAuthenticator(jwtKey))
                        .setAuthorizer(new RoleAuthoriser())
                        .setPrefix("Bearer")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey()
                .register(new AuthValueFactoryProvider
                        .Binder<>(JwtToken.class));

        environment.jersey()
                .register(new AuthController(new AuthService(
                        jwtKey, new AuthDao(), databaseConnector)));
        environment.jersey()
                .register(new JobRoleController(new JobRoleService(
                        new JobRoleDao(), databaseConnector)));
        environment.jersey()
                .register(new ApplicationController(new ApplicationService(
                        new ApplicationDao(), databaseConnector)));
        environment.jersey()
                .register(new BandController(new BandService(
                        new BandDao(), databaseConnector)));
        environment.jersey()
                .register(new LocationController(new LocationService(
                        new LocationDao(), databaseConnector)));
        environment.jersey()
                .register(new CapabilityController(new CapabilityService(
                        new CapabilityDao(), databaseConnector)));
    }

}
