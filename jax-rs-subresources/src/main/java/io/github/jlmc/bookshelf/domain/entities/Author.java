package io.github.jlmc.bookshelf.domain.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

/**
 * A simple embeddable POJO modelling an Author.
 */
@Embeddable
public class Author {

    @Column(name = "author", nullable = false)
    private String name;

    public Author() {
    }

    private Author(String name) {
        this.name = name;
    }

    public static Author of(String name) {
        return new Author(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Author{name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
