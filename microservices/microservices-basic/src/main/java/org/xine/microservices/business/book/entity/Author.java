package org.xine.microservices.business.book.entity;

public class Author {

    private Long id;

    private String name;

    protected Author() {
    }

    private Author(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Author of(String name) {
        return new Author(name);
    }

}
