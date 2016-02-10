package org.xine.business.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xine.business.control.Messenger;

@Path("decorators")
@Stateless
@Produces(MediaType.TEXT_PLAIN)
public class DecoratorsResource {

    @Inject
    Messenger messenger;

    @GET
    public String hey() {
        return this.messenger.morning();
    }

}
