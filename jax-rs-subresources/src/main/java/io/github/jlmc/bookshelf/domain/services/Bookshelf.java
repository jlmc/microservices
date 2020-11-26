package io.github.jlmc.bookshelf.domain.services;

import io.github.jlmc.bookshelf.domain.entities.Book;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Stateless
public class Bookshelf {

    @PersistenceContext
    EntityManager em;

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Book> findAll() {
        return em.createQuery("select b from Book b order by b.id", Book.class).getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public boolean exists(String isbn) {
        try {
            return em.createQuery("select true from Book b where b.isbn = :isbn", Boolean.class)
                    .setParameter("isbn", isbn)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
    }


    public Book findByIsbn(String isbn) {
        return em.createQuery("select b from Book b where b.isbn = :isbn", Book.class)
                .setParameter("isbn", isbn)
                .setMaxResults(1)
                .getSingleResult();
    }

    public Optional<Book> findByIsbnOptional(String isbn) {
        try {
            return Optional.of(findByIsbn(isbn));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Book add(Book book) {
        em.persist(book);
        em.flush();
        return book;
    }

}
