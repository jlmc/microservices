package org.jcosta.bookstore.book.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Book {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private String id;
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Book of(String id, String name) {
        Book book = new Book();
        book.id = id;
        book.name = name;
        return book;
    }
}
