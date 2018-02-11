package org.xine.books.boundary;


import org.xine.books.entity.Book;

import javax.ejb.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
public class BookManager {

    private final Map<String, Book> books = new HashMap<>();

    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }

    public Book find(String isbn) {
        return books.get(isbn);
    }

    public Book add (Book book) {
        Book of = Book.of(book.getIsbn(), book.getName());
        this.books.put(of.getIsbn(), of);
        return of;
    }

    public Book update (String isbn, Book book) {
        Book of = Book.of(isbn, book.getName());
        this.books.put(of.getIsbn(), of);
        return of;
    }

}
