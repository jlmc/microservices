package org.xine.business.books.boundary;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.xine.business.books.entity.Book;
import org.xine.business.crud.control.CrudService;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class BookManager {

    @EJB
    private CrudService crudService;

    public Book createBook(final String isbn, final String name) {
        final Book book = new Book(isbn, name);
        return this.crudService.create(book);
    }

    public int totalOfBooks() {
        final int size = this.crudService.findWithNamedQuery(Book.ALL).size();
        return size;
    }

}
