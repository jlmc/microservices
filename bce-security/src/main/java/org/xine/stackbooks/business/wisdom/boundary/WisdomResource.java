package org.xine.stackbooks.business.wisdom.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xine.stackbooks.business.wisdom.control.CustomSecuredWisdomStorage;

@Stateless
@Path("wisdom")
@Produces(MediaType.TEXT_PLAIN)
public class WisdomResource {

    @Inject
    CustomSecuredWisdomStorage storage;

    @GET
    public String wisdom() {
        return this.storage.wisdom();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String wisdom(@FormParam("wisdom") String wisdom) {
        this.storage.wisdom(wisdom);
        return "thanks!";
    }

}
