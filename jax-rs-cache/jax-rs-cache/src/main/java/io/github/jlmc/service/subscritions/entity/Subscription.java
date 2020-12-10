package io.github.jlmc.service.subscritions.entity;

import io.github.jlmc.chassis.jsonb.JsonbRepresentation;
import io.github.jlmc.chassis.validations.Validations;
import io.github.jlmc.service.books.entity.Book;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.Objects;

@Schema(name = "Subscription")

@JsonbRepresentation
@JsonbPropertyOrder({"id", "reader", "book"})
@Entity
public class Subscription {

    @Schema(hidden = true, readOnly = true)
    @Null(groups = {Validations.SubscriptionCreation.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(name = "reader", example = "Max Payner", required = true, maxLength = 100)
    @Size(max = 100)
    @NotBlank
    private String reader;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, updatable = false)
    private Book book;

    @Schema(readOnly = true)
    //@Null(groups = {Validations.SubscriptionCreation.class})
    @Enumerated(EnumType.STRING)
    private Status status = Status.SUBMITTED;

    @JsonbTransient
    @Version
    @Column(name = "last_modified", nullable = false)
    private Instant lastModified;

    protected Subscription() {
    }

    private Subscription(String reader, Book book) {
        this.reader = reader;
        this.book = book;
    }

    public static Subscription of(String reader, Book book) {
        return new Subscription(reader, book);
    }

    public Integer getId() {
        return id;
    }

    public String getReader() {
        return reader;
    }

    public Book getBook() {
        return book;
    }

    public Status getStatus() {
        return status;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return "Subscription{" +
                "id=" + id +
                ", reader='" + reader + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Subscription)) return false;
        Subscription that = (Subscription) o;
        return getId() != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public Subscription approve() {
        this.status = Status.APPROVED;
        return this;
    }

    @Schema(hidden = true, readOnly = true)
    @JsonbTransient
    public boolean isApproved() {
        return Status.APPROVED == this.status;
    }


}
