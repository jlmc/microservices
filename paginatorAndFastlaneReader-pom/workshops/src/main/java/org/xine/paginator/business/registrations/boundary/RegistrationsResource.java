package org.xine.paginator.business.registrations.boundary;

import java.net.URI;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.xine.paginator.business.registrations.control.VatCalculator;
import org.xine.paginator.business.registrations.entity.Registration;

@Stateless
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Path("registrations")
public class RegistrationsResource {

    @Inject
    Registrations registrations;

    @Inject
    VatCalculator calculator;

    @POST
    public Response register(final Registration request, @Context final UriInfo info) {
        final JsonObject registration = this.registrations.register(request);
        final long id = registration.getInt(Registrations.CONFIRMATION_ID);
        final URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).entity(registration).build();
    }

    @GET
    @Path("{id}")
    public Registration find(@PathParam("id") final long registrationId) {
        return this.registrations.find(registrationId);
    }

    @GET
    public Response all() {
        final JsonArray registrationList = this.registrations.allAsJson();
        if (registrationList == null || registrationList.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(registrationList).build();
    }

    @GET
    @Path("{id}/dummy")
    public Registration dummy(@PathParam("id") final int registrationId) {
        return new Registration(registrationId);
    }

}
