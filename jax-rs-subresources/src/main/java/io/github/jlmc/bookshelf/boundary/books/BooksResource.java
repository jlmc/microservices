package io.github.jlmc.bookshelf.boundary.books;

import io.github.jlmc.bookshelf.domain.entities.Book;
import io.github.jlmc.bookshelf.domain.services.Bookshelf;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("/books")
@RequestScoped
@Produces({MediaType.APPLICATION_JSON})
public class BooksResource {

    @Inject
    Bookshelf bookshelf;

    @Context
    UriInfo uriInfo;

    @Context
    ResourceContext context;

    @GET
    public Response books() {
        return Response.ok(bookshelf.findAll()).build();
    }

    @Path("/{isbn}/author")
    public AuthorResource author(@PathParam("isbn") String isbn) {
        Book book = bookshelf.findByIsbn(isbn);
        return new AuthorResource(book);
    }

    @Path("/{isbn}/loans")
    public LoanResource loans(@PathParam("isbn") String isbn) {
        LoanResource loanResource = context.getResource(LoanResource.class);
        loanResource.setIsbn(isbn);

        return loanResource;
    }

    @GET
    @Path("/{isbn}")
    public Response bookByIsbn(@PathParam("isbn") String isbn) {
        return Response.ok(bookshelf.findByIsbn(isbn)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Book book) {
        if (bookshelf.exists(book.getIsbn())) {
            return Response.status(Response.Status.CONFLICT).build();
        }

        Book add = bookshelf.add(book);

        URI uri = uriInfo.getAbsolutePathBuilder().path("{isbn}").build(add.getIsbn());

        return Response.created(uri).entity(add).build();
    }


}
