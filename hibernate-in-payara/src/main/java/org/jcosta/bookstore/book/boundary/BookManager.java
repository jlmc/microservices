package org.jcosta.bookstore.book.boundary;

import org.jcosta.bookstore.ResourceAlreadyExistsException;
import org.jcosta.bookstore.book.entity.Book;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Stateless
public class BookManager {

    @PersistenceContext
    EntityManager entityManager;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Collection<Book> search() {

        //entityManager.createQuery("select b from Book b", Book.class).unwrap(Query.class).stream();

        return entityManager.createQuery("select b from Book b", Book.class).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Book create(Book book) {
        Book existing = entityManager.find(Book.class, book.getId());
        if (existing != null)
            throw new ResourceAlreadyExistsException();



        this.entityManager.persist(book);
        return book;
    }

    public Book find(String id) {

        return this.entityManager.createQuery("select b from Book b where b.id = :id", Book.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void remove(String id) {
        this.entityManager.remove(this.find(id));
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update(String id, Book book) {
        Book saved = this.find(id);
        saved.setName(book.getName());
    }
}
