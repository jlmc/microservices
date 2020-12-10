package io.github.jlmc.service.ping.boundary;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Tag(name = "Ping", description = "Ping Api")

@Path("ping")
public class PingResource {

    @Inject
    @ConfigProperty(name = "message")
    String message;    

    @GET
    public String ping() {
        return this.message + " Jakarta EE 8 with MicroProfile 3+!";
    }

}
