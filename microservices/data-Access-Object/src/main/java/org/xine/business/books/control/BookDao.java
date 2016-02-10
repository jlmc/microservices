package org.xine.business.books.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.xine.business.books.entity.Book;
import org.xine.business.crud.control.DAO;

@Stateless
@Local(DAO.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BookDao implements DAO<Integer, Book> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Book create(final Book t) {
        this.em.persist(t);
        return t;
    }

    @Override
    public Book find(final Integer id) {
        return this.em.find(Book.class, id);
    }

    @Override
    public void delete(Book t) {
        t = this.em.merge(t);
        this.em.remove(t);
    }

    @Override
    public Book update(final Book t) {
        return this.em.merge(t);
    }

    @Override
    public Collection<Book> findByNamedQuery(final String queryName) {
        return this.em.createNamedQuery(queryName, Book.class)
                .getResultList();
    }

    @Override
    public Collection<Book> findByNamedQuery(final String queryName
            , final int resultLimit) {
        return this.em.createNamedQuery(queryName, Book.class)
                .setMaxResults(resultLimit)
                .getResultList();
    }

    @Override
    public List<Book> findByNamedQuery(
            final String namedQueryName
            , final Map<String, Object> parameters) {
        return findByNamedQuery(namedQueryName, parameters, 0);
    }

    @Override
    public List<Book> findByNamedQuery(
            final String namedQueryName
            , final Map<String, Object> parameters
            , final int resultLimit) {

        final Query query = this.em.createNamedQuery(namedQueryName, Book.class);

        parameters.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));

        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }

        return query.getResultList();
    }

}
