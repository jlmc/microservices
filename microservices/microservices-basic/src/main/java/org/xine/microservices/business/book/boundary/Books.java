package org.xine.microservices.business.book.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.xine.microservices.business.book.control.BookStorage;
import org.xine.microservices.business.book.entity.Book;

@Stateless
public class Books {

    @Inject
    BookStorage storage;

    public List<Book> search() {
        return this.storage.search();
    }

    public Book save(Book book) {
        return this.storage.save(book);
    }

    public Book search(Long id) {
        return this.storage.search(id);
    }

}
