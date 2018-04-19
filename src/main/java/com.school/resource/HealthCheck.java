package com.school.resource;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Component
@Path("/health")
public class HealthCheck {
    @GET
    @Path("/check")
    @Produces(MediaType.APPLICATION_JSON)
    public String healthCheck(@QueryParam("id") String id) {
        return "hello world!";
    }
}
