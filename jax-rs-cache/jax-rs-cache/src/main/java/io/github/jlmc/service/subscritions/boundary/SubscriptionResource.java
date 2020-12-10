package io.github.jlmc.service.subscritions.boundary;

import io.github.jlmc.chassis.validations.Validations;
import io.github.jlmc.service.subscritions.control.Subscriptions;
import io.github.jlmc.service.subscritions.entity.Subscription;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@Tag(name = "Subscriptions", description = "Subscriptions Api")

@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionResource {

    @Inject
    Subscriptions subscriptions;

    @GET
    @Path("/{id: \\d+}")
    public Response findById(@PathParam("id") Integer id) {
        Subscription subscription = subscriptions.findById(id);

        return Response.ok(subscription).build();
    }

    @POST
    public Response create(@RequestBody
                           @Valid @ConvertGroup(from = Default.class, to = Validations.SubscriptionCreation.class)
                           Subscription subscription) {
        Subscription created = subscriptions.create(subscription);

        URI uri = UriBuilder.fromMethod(SubscriptionResource.class, "findById").build(created.getId());
        return Response.created(uri)
                .entity(created)
                .build();
    }

    //resources/{resource_identifier}/close

    @POST
    @Path("/{id: \\d+}/approvement")
    public Response approve(@PathParam("id") Integer id) {
        subscriptions.approve(id);

        return Response.noContent().build();
    }

    @GET
    @Path("/{id: \\d+}/approvement")
    public Response isApproved(@PathParam("id") Integer id) {

        boolean isApproved = subscriptions.isApproved(id);

        return Response.ok(isApproved).build();
    }


}
