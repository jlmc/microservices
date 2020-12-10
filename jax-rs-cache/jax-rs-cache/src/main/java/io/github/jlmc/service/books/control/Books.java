package io.github.jlmc.service.books.control;

import io.github.jlmc.chassis.persistence.DAO;
import io.github.jlmc.service.books.entity.Book;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Stateless
public class Books {

    @Inject
    DAO<Book, Integer> dao;

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Book> all() {
        return dao.all();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Book findById(Integer id) {
        return dao.findByIdOrElseThrow(id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Book add(Book book) {
        return dao.saveAndFlush(book);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Book update(Integer id, Book other) {
        Book existing = dao.findByIdOrElseThrow(id);

        existing.update(other);

        dao.flush();

        return dao.refresh(existing);
    }
}
