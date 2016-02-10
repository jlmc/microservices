package org.xine.business.books.entity;

import java.io.Serializable;

public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private int numberOfPages;
    private String name;

    public BookDTO(final int numberOfPages, final String name) {
        this.numberOfPages = numberOfPages;
        this.name = name;
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

}
