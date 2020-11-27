# Self Inject Patterm

- Motivation: If we need a separate transaction or want to call that method asynchronously and the business target code is in the same bean class.

- EJB 3.1 are "POJOs with built-in aspects". You get transactions, security, concurrency and monitoring for free with only negligible overhead and without any XML configuration. The aspects, however, can only work in case the container is able to intercept the calls.
Call interception works if the injected (or looked-up) instances are used. this keyword does not work--the call is obviously not intercepted. To get "this with aspects" you will have to use an injected (or looked-up) instance or use the SessionContext#getBusinessObject method:


- CDI currently supports circular dependency (in your case self-injecting) when one of the beans has normal scope: @RequestScoped, @SessionScoped, @ApplicationScoped, @ConversationScoped.


```java
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
    }
}
```


# Build
mvn clean package && docker build -t io.github.jlmc/self-inject .

# RUN

docker rm -f self-inject || true && docker run -d -p 8080:8080 -p 4848:4848 --name self-inject io.github.jlmc/self-inject