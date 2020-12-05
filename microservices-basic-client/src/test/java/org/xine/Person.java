package org.xine;

import java.util.Objects;

public class Person {

    private Long id;
    private String name;

    public Person(final Long id) {
        this.id = id;
    }

    public Person(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id == null) {
            return other.id == null;
        } else return this.id.equals(other.id);
    }

    @Override
    public String toString() {
        return "Person [id=" + this.id + ", name=" + this.name + "]";
    }

}
