package io.github.jlmc.bookshelf.boundary.books;

import io.github.jlmc.bookshelf.domain.entities.Book;
import io.github.jlmc.bookshelf.domain.services.Bookshelf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/books")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class BooksResource {

    @Inject
    Bookshelf bookshelf;

    @GET
    public Response books() {
        return Response.ok(bookshelf.findAll()).build();
    }

    @GET
    @Path("/{isbn}")
    public Response bookByIsbn(@PathParam("isbn") String isbn) {
        return Response.ok(bookshelf.findByIsbn(isbn)).build();
    }

}
