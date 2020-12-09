package io.github.jlmc.service.books.control;

import io.github.jlmc.chassis.persistence.DAO;
import io.github.jlmc.service.books.entity.Book;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Stateless
public class Books {

    @Inject
    DAO<Book, Integer> dao;

    public List<Book> all() {
        return dao.all();
    }

    public Book findById(Integer id) {
        return dao.findById(id)
                  .orElseThrow(() -> new NotFoundException("No book found with the identifier <" + id + ">"));
    }

    public Book add(Book book) {
        return dao.saveAndFlush(book);
    }
}
