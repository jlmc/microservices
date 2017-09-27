package org.jcosta.bookstore.book.boundary;

import org.jcosta.bookstore.book.entity.Book;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@Path("books")
@Produces({MediaType.APPLICATION_JSON})
public class BookResource {

    @Inject
    BookManager manager;

    @Context
    UriInfo info;

    @GET
    public Response search() {
        GenericEntity<Collection<Book>> list = new GenericEntity<Collection<Book>>(manager.search()) {};
        return Response.ok(list).build();
    }

    @POST
    public Response create(@Valid Book book) {
        Book duke = this.manager.create(book);
        final URI uri = info.getAbsolutePathBuilder().path("{id}").build(duke.getId());
        return Response.created(uri).entity(duke).build();
    }

    @GET
    @Path("{id}")
    public Response get(@PathParam("id") final String id) {
        Book book = manager.find(id);
        return Response.ok(book).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") final String id) {
        manager.remove(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") String id, @Valid Book book) {
        manager.update(id, book);
        return Response.noContent().build();
    }
}
