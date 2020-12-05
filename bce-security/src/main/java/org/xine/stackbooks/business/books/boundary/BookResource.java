package org.xine.stackbooks.business.books.boundary;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("book")
@Produces(MediaType.TEXT_PLAIN)
public class BookResource {

    @Inject
    Logger tracer;

    @GET
    public String book() {
        this.tracer.info("get Method: .... ");
        return "java EE rocks";
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String update(@FormParam("wisdom") String wisdom) {
        this.tracer.info("Post request:  " + wisdom);
        return "thanks!";
    }

}
