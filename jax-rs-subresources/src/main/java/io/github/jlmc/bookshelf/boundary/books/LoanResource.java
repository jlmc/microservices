package io.github.jlmc.bookshelf.boundary.books;

import io.github.jlmc.bookshelf.domain.entities.Loan;
import io.github.jlmc.bookshelf.domain.services.Bookshelf;
import io.github.jlmc.bookshelf.domain.services.Library;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.List;

/**
 * Sub REST resource implementation for loans.
 */
@RequestScoped
public class LoanResource {

    @Inject
    private Bookshelf bookshelf;

    @Inject
    Library library;

    private String isbn;

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loans() {
        List<Loan> books = library.allLoansByIsbn(isbn);

        return Response.ok(books).build();
    }

    @GET
    @Path("/{loanId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loan(@PathParam("loanId") String loanId) {
        Loan loan = library.loanInfo(loanId);
        return Response.ok(loan).build();
    }

    @DELETE
    @Path("/{loanId}")
    public Response returnBook(@PathParam("loanId") String loanId) {
        library.returnBook(isbn, loanId);
        return Response.ok().build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response lendBook(Loan loan) {
        library.lendBook(isbn, loan);

        URI location = UriBuilder.fromResource(BooksResource.class)
                .path("/{isbn}/loans/{loanId}")
                .resolveTemplate("isbn", isbn)
                .resolveTemplate("loanId", loan.getId())
                .build();

        return Response.created(location).build();
    }

}
