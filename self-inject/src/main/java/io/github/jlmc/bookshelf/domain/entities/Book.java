package io.github.jlmc.bookshelf.domain.entities;

import io.github.jlmc.bookshelf.core.jsonb.JsonbRepresentation;
import org.hibernate.annotations.NaturalId;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@JsonbRepresentation

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(unique = true, nullable = false)
    private String isbn;

    private String title;

    @Embedded
    private Author author;

    @JsonbTransient
    @OneToMany(mappedBy = "book",
            orphanRemoval = true,
              cascade = {CascadeType.MERGE,
                         CascadeType.PERSIST,
                         CascadeType.DETACH,
                         CascadeType.REFRESH})
    private List<Loan> loans = new ArrayList<>();

    public Book() {
    }

    private Book(String isbn, String title, Author author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }

    public static Book of(String isbn, String title, String author) {
        return new Book(isbn, title, Optional.ofNullable(author).map(Author::of).orElse(null));
    }

    // ...

    public Long getId() {
        return id;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    // ...


    public Collection<Loan> getLoans() {
        return List.copyOf(this.loans);
    }

    public void setLoans(Collection<Loan> loans) {
        this.loans.clear();
        this.loans.addAll(loans);
    }

    public void addLoan(Loan loan) {
        loan.setBook(this);
        loans.add(loan);
    }

    public void removeLoan(Loan loan) {
        int index = loans.indexOf(loan);
        if (index > -1) {
            Loan l = loans.remove(index);
            l.setBook(null);
        }
    }
}
