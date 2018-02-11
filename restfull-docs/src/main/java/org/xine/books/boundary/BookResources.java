package org.xine.books.boundary;

import org.xine.books.entity.Book;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

@Path("books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResources {

    @Inject
    BookManager manager;

    @Context
    UriInfo uriInfo;

    @GET
    public List<Book> allBooks() {
        return manager.getAll();
    }

    @GET
    @Path("{isbn}")
    public Book get(@PathParam("isbn") String isbn) {
        return manager.find(isbn);
    }

    @POST
    public Response create(@Valid Book book) {
        Book of = manager.add(book);
        URI uri = uriInfo.getAbsolutePathBuilder().path("{isbn}").build(of.getIsbn());
        return Response.created(uri).entity(of).build();
    }
}
