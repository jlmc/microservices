package io.github.jlmc.bookshelf.boundary.books;

import io.github.jlmc.bookshelf.core.auditing.Auditable;
import io.github.jlmc.bookshelf.domain.entities.Book;
import io.github.jlmc.bookshelf.domain.entities.Loan;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Startup
@Singleton
public class Initializer {

    @PersistenceContext
    EntityManager em;

    //@javax.ejb.EJB // legacy previous to java ee 7
    //@Inject // works with java ee 8
    Initializer self;

    @Resource
    SessionContext context;

    @PostConstruct
    public void populateSomeBooks() {

        this.self = context.getBusinessObject(Initializer.class);

        //this.context.setRollbackOnly();

        System.out.println("---- Main Thread: " + Thread.currentThread().getId());

        self.executeAsync();
    }

    @Auditable(text = "-- if the self inject works then the Audit Interceptor should be called! --")
    @Asynchronous
    @org.jboss.ejb3.annotation.TransactionTimeout(unit = TimeUnit.MINUTES, value = 1)
    public void executeAsync() {
        System.out.println("---- Async Thread: " + Thread.currentThread().getId());
        Book b1 = Book.of("0001", "The Lion, the Witch and the Wardrobe", "C. S. Lewis");
        Book b2 = Book.of("0002", "She: A History of Adventure", "H. Rider Haggard");
        Book b3 = Book.of("0003", "The Adventures of Pinocchio (Le avventure di Pinocchio)", "Carlo Collodi");
        Book b4 = Book.of("0004", "The Da Vinci Code", "Dan Brown");
        Book b5 = Book.of("0005", "The Alchemist (O Alquimista)", "Paulo Coelho");

        b1.addLoan(Loan.createLoan("JC", LocalDate.parse("2020-01-05"), LocalDate.parse("2020-01-10"), b1));
        b1.addLoan(Loan.createLoan("Alvaro", LocalDate.parse("2020-01-11"), LocalDate.parse("2020-01-20"), b1));
        b2.addLoan(Loan.createLoan("JC", LocalDate.parse("2021-01-11"), LocalDate.parse("2021-01-20"), b2));

        Stream.of(
                b1,
                b2,
                b3,
                b4,
                b5
        ).forEach(em::persist);
    }
}
