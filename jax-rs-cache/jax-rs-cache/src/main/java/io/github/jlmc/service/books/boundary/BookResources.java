package io.github.jlmc.service.books.boundary;

import io.github.jlmc.chassis.validations.Validations;
import io.github.jlmc.service.books.control.Books;
import io.github.jlmc.service.books.entity.Book;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.Date;
import java.util.List;

@Tag(name = "Book", description = "Book Api")

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResources {

    @Inject
    Books books;

    @Context
    UriInfo uriInfo;

    @GET
    public Response all() {
        List<Book> all = books.all();

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(10);
        cacheControl.setPrivate(false);
        cacheControl.setNoTransform(true);

        return Response.ok(all).cacheControl(cacheControl).build();
    }


    @Operation(description = "Getting Book")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "If it has been changed since the timestamp provided, the server will send back a 200, “OK,” response with the new representation of the resource.",
                    content = {@Content(schema = @Schema(implementation = Book.class))},
                    headers = {
                            @Header(name = "Cache-Control", schema = @Schema(implementation = String.class, example = "no-transform, max-age=60")),
                            @Header(name = "Last-Modified", schema = @Schema(implementation = Date.class, example = "Tue, 01 Jan 2019 00:00:00 GMT"))}
            ),
            @APIResponse(
                    responseCode = "304",
                    description = "Otherwise, If it hasn’t been changed, the server will respond with 304, “Not Modified,” and return no representation.",
                    headers = {
                            @Header(name = "Cache-Control", schema = @Schema(implementation = String.class, example = "no-transform, max-age=60"))
                    }
            )
    })
    @GET
    @Path("/{id: \\d+}")
    public Response findById(@PathParam("id") Integer id,
                             @Context Request request,
                             @Context HttpHeaders httpHeaders) {

        Book book = books.findById(id);

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(60);
        cacheControl.setPrivate(false);
        cacheControl.setNoTransform(true);

        //- When a service receives this GET request, it checks to see if its resource has been modified since the date provided within the `If-Modified-Since` header.
        //- If it has been changed since the timestamp provided, the server will send back a 200, “OK,” response with the new representation of the resource.
        //- Otherwise, If it hasn't been changed, the server will respond with 304, “Not Modified,” and return no representation.
        //- In both cases, the server should send an updated Cache-Control and Last-Modified header if appropriate.
        Response.ResponseBuilder responseBuilder = request.evaluatePreconditions(Date.from(book.getLastModified()));
        if (responseBuilder != null) {
            // sending 304 not modified
            return responseBuilder
                    .cacheControl(cacheControl)
                    .build();
        }

        return Response.ok(book)
                .cacheControl(cacheControl)
                .lastModified(Date.from(book.getLastModified()))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(
            @Valid
            @ConvertGroup(from = Default.class, to = Validations.Creation.class)
                    Book book) {
        Book created = books.add(book);

        URI uri = uriInfo.getAbsolutePathBuilder().path("/{id}").build(created.getId());

        return Response.created(uri).entity(created).build();
    }

    @Operation(description = "Update Book")
    @PUT
    @Path("/{id: \\d+}")
    public Response update(@PathParam("id") Integer id,
                           @Context Request request,
                           @RequestBody(content = @Content(schema = @Schema(implementation = Book.class)))
                           @Valid @ConvertGroup(from = Default.class, to = Validations.Update.class) Book book) {

        Book existing = books.findById(id);
        Date lastModification = Date.from(existing.getLastModified());

        Response.ResponseBuilder builder = request.evaluatePreconditions(lastModification);

        if (builder != null) {
            return builder.build();
        }

        books.update(id, book);
        return Response.noContent().build();
    }
}
