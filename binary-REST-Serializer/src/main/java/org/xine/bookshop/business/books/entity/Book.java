package org.xine.bookshop.business.books.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long imdb;
    private String name;

    protected Book() {
    }

    public Book(final Long imdb, final String name) {
        this.imdb = imdb;
        this.name = name;
    }

    public Long getImdb() {
        return this.imdb;
    }

    public void setImdb(final Long imdb) {
        this.imdb = imdb;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Book [imdb=" + this.imdb + ", name=" + this.name + "]";
    }

}
