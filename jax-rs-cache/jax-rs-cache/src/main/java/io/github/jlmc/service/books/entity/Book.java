package io.github.jlmc.service.books.entity;

import io.github.jlmc.chassis.jsonb.JsonbRepresentation;
import io.github.jlmc.chassis.validations.Validations;
import io.github.jlmc.service.books.control.Books;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import java.time.Instant;

@Schema(name = "Book")

@JsonbRepresentation
@JsonbPropertyOrder({"id", "title"})

@Entity
public class Book {

    @Schema(hidden = true, readOnly = true)
    @Null(groups = {Validations.Creation.class, Validations.Update.class})

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String title;

    @JsonbTransient
    @Version
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public Book update(Book other) {
        this.title = other.title;
        return this;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
