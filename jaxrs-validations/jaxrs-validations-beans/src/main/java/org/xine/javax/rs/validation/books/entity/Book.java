package org.xine.javax.rs.validation.books.entity;

import java.io.Serializable;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Book implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @NotNull
    private Long id;
    @NotNull
    @Size(min = 3, max = 10)
    private String title;
    
    Book(){}
    
    public static Book of(Long id, String title) {
        final Book b = new Book();
        b.id = id;
        b.title = title;
        return b;
    }
    
    public Long getId() {
        return this.id;
    }
    
    protected void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;
        return Objects.equals(this.id, other.id);
    }

}
