package org.xine.business.books.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery(name=Book.ALL,query="Select b From Book b"),
    @NamedQuery(name=Book.BY_NAME,query="Select b From Book b where b.name = :name"),
    @NamedQuery(name=Book.BY_NAME_AND_PAGES,query="Select b From Book b where b.name = :name and b.numberOfPages =:pages"),
    @NamedQuery(name=Book.ALL_DTO,query="Select new org.xine.business.books.entity.BookDTO(b.numberOfPages,b.name) From Book b")
})
public class Book {

    public final static String ALL = "org.xine.business.books.entity.Book.ALL";
    public final static String ALL_DTO = "org.xine.business.books.entity.Book.ALL_DTO";
    public final static String BY_NAME = "org.xine.business.books.entity.Book.BY_NAME";
    public final static String BY_NAME_AND_PAGES = "org.xine.business.books.entity.Book.BY_NAME_AND_PAGES";

    @Id
    private String isbn;
    private String name;
    private int numberOfPages;

    public Book() {
    }

    public Book(final String isbn,final String name) {
        this.isbn = isbn;
        this.name = name;
    }

    public Book(final String isbn, final String name, final int numberOfPages) {
        this(isbn,name);
        this.numberOfPages = numberOfPages;
    }


    public String getIsbn() {
        return this.isbn;
    }

    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getNumberOfPages() {
        return this.numberOfPages;
    }

    public void setNumberOfPages(final int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }



    @Override
    public String toString() {
        return "org.xine.business.books.entity.Book[isbn=" + this.isbn + "]";
    }

}
