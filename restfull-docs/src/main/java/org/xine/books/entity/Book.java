package org.xine.books.entity;

import org.xine.validation.ISBN;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Book implements Serializable {

    @NotNull
    @ISBN
    private String isbn;

    @NotNull
    @Size(min = 1, max = 250)
    private String name;

    protected Book() {
    }

    public static Book of (String isbn, String name) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("The book isbn must not be null or blank");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The book isbn must not be null or blank");
        }

        return new Book(isbn, name);
    }

    private Book(String isbn, String name) {
        this.isbn = isbn;
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
