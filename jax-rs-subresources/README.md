# Jax-Rs Sub Resources

We're going to take a look at how to implement simple sub-resource locator methods.  

We'll have a look at how you can obtain CDI sub-resource instances from the root resource, and we're going to have a look at how you can pass context information from the root to the sub-resources:

- Get Author     -  GET      /resources/books/{isbn}/author
- Get Loans      -  GET      /resources/books/{isbn}/loans
- Take out Book  -  POST     /resources/books/{isbn}/loans
- Get Loan       -  GET      /resources/books/{isbn}/loans/{id}
- Return Book    -  DELETE   /resources/books/{isbn}/loans/{id}


Let's start with the authors. In BookResource.java, add a resource locator method.  
A resource locator method is a simple method that is only annotated using the @Path annotation.  
In this case, we use @Path("/{isbn}/author"). The return type of a resource locator method is another resource.  
In this case, it's the AuthorResource locator method. Thus, we create the AuthorResource locator:

```
@Path("/{isbn}/author")
public AuthorResource author(@PathParam("isbn") String isbn) {
    Book book = bookshelf.findByISBN(isbn);
    return new AuthorResource(book);
}
```


It produces APPLICATION_JSON. We get a reference to our book in the constructor. 
Next up in this sub-resource, we can add the usual GET, POST, or PUT annotated HTTP methods again. 
In this case, we have one GET method annotated, which gets the author of our book:

```
@Produces(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final Book book;
    AuthorResource(Book book) {
        this.book = book;
    }
    
    @GET
    public Author get() {
        return book.getAuthor();
    }
}
```


But, what if we want to use CDI injection? 
If we want to do that, we need to take a different approach.  
First, we need to get a reference to `javax.ws.rs.container.ResourceContext`; make sure you use the right one. By using this `ResourceContext`, we can get hold of a reference that is fully CDI injected. 
gain, we annotated using `@Path`, returned `loanResource`, and this time we used `context.getResource` from `LoanResource.class`. This returns a fully injected loanResource instance:


```java
@RequestScoped
public class BookResource {
    
    @Inject
    private Bookshelf bookshelf;
    
    @javax.ws.rs.core.Context
    private ResourceContext context;
    
    @Path("/{isbn}/loans")
    public LoanResource loans(@PathParam("isbn") String isbn) {
        LoanResource loanResource =
        context.getResource(LoanResource.class);
        loanResource.setIsbn(isbn);
        return loanResource;
    }
}
```


```java
@RequestScoped
public class LoanResource {

    @Inject
    private Bookshelf bookshelf;

    @Inject
    Library library;

    private String isbn;

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loans() {
        List<Loan> books = library.allLoansByIsbn(isbn);

        return Response.ok(books).build();
    }

}
```



# Build
mvn clean package && docker build -t io.github.jlmc/jax-rs-subresources .

# RUN

docker rm -f jax-rs-subresources || true && docker run -d -p 8080:8080 -p 4848:4848 --name jax-rs-subresources io.github.jlmc/jax-rs-subresources 

# System Test

Switch to the "-st" module and perform:

mvn compile failsafe:integration-test