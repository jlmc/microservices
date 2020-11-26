package io.github.jlmc.bookshelf.boundary.books;

import io.github.jlmc.bookshelf.domain.entities.Author;
import io.github.jlmc.bookshelf.domain.entities.Book;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private Book book;

    public AuthorResource(Book book) {
        this.book = book;
    }

    @GET
    public Author get() {
        return book.getAuthor();
    }
}
