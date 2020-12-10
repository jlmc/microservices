package io.github.jlmc.service.subscritions.boundary;

import io.github.jlmc.chassis.hashing.Encryption;
import io.github.jlmc.chassis.hashing.Encryptor;
import io.github.jlmc.chassis.validations.Validations;
import io.github.jlmc.service.subscritions.control.Subscriptions;
import io.github.jlmc.service.subscritions.entity.Subscription;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Date;

@Tag(name = "Subscriptions", description = "Subscriptions Api")

@Path("/subscriptions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubscriptionResource {

    @Inject
    Subscriptions subscriptions;

    @Inject
    @Encryption(type = Encryption.Type.MD5)
    Encryptor encryptor;

    @GET
    @Path("/{id: \\d+}")
    public Response findById(@PathParam("id") Integer id, @Context Request request) {
        Subscription subscription = subscriptions.findById(id);

        String hash = subscription.hash(encryptor);
        EntityTag tag = new EntityTag(hash, true);

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(60);
        cacheControl.setPrivate(false);
        cacheControl.setNoTransform(true);

        Response.ResponseBuilder builder = request.evaluatePreconditions(tag);
        if (builder != null) {
            // sending 304 not modified
            return builder
                    .cacheControl(cacheControl)
                    .build();
        }

        return Response.ok(subscription)
                .cacheControl(cacheControl)
                .tag(tag)
                .lastModified(Date.from(subscription.getLastModified()))
                .build();
    }

    @POST
    public Response create(@RequestBody
                           @Valid @ConvertGroup(from = Default.class, to = Validations.SubscriptionCreation.class)
                                   Subscription subscription, @Context UriInfo uriInfo) {
        Subscription created = subscriptions.create(subscription);

        URI uri = uriInfo.getAbsolutePathBuilder().path("/{id}").build(created.getId());

        return Response.created(uri)
                .entity(created)
                .build();
    }

    //resources/{resource_identifier}/close

    @POST
    @Path("/{id: \\d+}/approvement")
    public Response approve(@PathParam("id") Integer id, @Context Request request) {
        Subscription existing = subscriptions.findById(id);
        String hash = existing.hash(encryptor);
        EntityTag tag = new EntityTag(hash, true);
        Date lastModification = Date.from(existing.getLastModified());

        Response.ResponseBuilder builder = request.evaluatePreconditions(lastModification, tag);

        if (builder != null) {
            // Preconditions not met!
            builder.build();
        }

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
