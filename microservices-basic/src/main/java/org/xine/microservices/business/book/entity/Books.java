package org.xine.microservices.business.book.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Books {

    @XmlElement(name = "book")
    public List<Book> books = new ArrayList<>();

    public void addBooks(Collection<Book> bs) {
        this.books.addAll(bs);
    }

    public List<Book> getBooks() {
        return this.books;
    }

}
