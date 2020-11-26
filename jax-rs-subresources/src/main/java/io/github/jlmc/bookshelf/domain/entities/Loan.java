package io.github.jlmc.bookshelf.domain.entities;

import io.github.jlmc.bookshelf.core.jsonb.JsonbRepresentation;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@JsonbRepresentation
@JsonbPropertyOrder({"id", "username", "start", "end"})

@Entity
public class Loan {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "username")
    private String username;

    @Column(name = "period_start")
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate start;

    @Column(name = "period_end")
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate end;

    @JsonbTransient

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    public Loan() {
        this(UUID.randomUUID().toString());
    }

    private Loan(String loanId) {
        this.id = loanId;
    }

    private Loan(String username, LocalDate start, LocalDate end, Book book) {
        this();
        this.username = username;
        this.start = start;
        this.end = end;
        this.book = book;
    }

    public static Loan createLoan(String username, LocalDate start, LocalDate end, Book book) {
        final Loan loan = new Loan(username, start, end, book);
        return loan;
    }

    public static Loan withTheId(String loanId) {
        final Loan loan = new Loan();
        loan.id = loanId;
        return loan;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Loan{id='" + id + '\'' + ", username='" + username + '\'' + ", start=" + start + ", end=" + end + '}';
    }

    // ...

    protected void setBook(Book book) {
        this.book = book;
    }
}
