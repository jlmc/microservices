package org.xine.javax.rs.validation.books.boundary;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.formatter.Formatters;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.xine.javax.rs.validation.JAXRSConfiguration;
import org.xine.javax.rs.validation.books.entity.Book;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;


@RunWith(Arquillian.class)
public class BookResourceIT {
    
    private static final Logger LOGGER = Logger.getLogger(BookResourceIT.class.getName());
    
    static final Book book = Book.of(1L, "simple");
    
    @Inject
    BookResource bookResource;

    @Deployment
    public static Archive<?> createDeployment() {
        final WebArchive war = 
                ShrinkWrap.create(WebArchive.class, "jaxrsvalidationbeans.war")
                        .addClasses(BookResource.class, JAXRSConfiguration.class, Book.class).
                        addAsResource("ValidationMessages.properties", "ValidationMessages.properties").
                        addAsResource("ValidationMessages_pt.properties", "ValidatValidationMessages_pt.properties").
                addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        // System.out.println(archive.toString(true));
        
        LOGGER.info(war.toString(Formatters.VERBOSE));
        
        return war;
    }

    @Test
    @InSequence(1)
    @RunAsClient
    public void shouldCreateBook(@ArquillianResource final URL baseURL) {
        System.out.println("base: " + baseURL);
        
        final Client client = 
                ClientBuilder.newBuilder().
                              register(JacksonJsonProvider.class).build();
        
       
        final Response response = client.target(baseURL + "resources/books").
                                   request(MediaType.APPLICATION_JSON).
                                   post(Entity.entity(book, MediaType.APPLICATION_JSON));
        response.bufferEntity();
    
        logResponse("shouldCREATEBook", response);
        assertEquals(book, response.readEntity(Book.class));
    }
    
    @Test
    @InSequence(2)
    @RunAsClient
    public void shouldGetBook(@ArquillianResource final URL baseURL) {
        System.out.println("base: " + baseURL);
        
        final Client client = 
                ClientBuilder.newBuilder().
                register(JacksonJsonProvider.class).build();
        
        
        final Response response = client.target(baseURL + "resources/books/{id}").
                resolveTemplate("id", 1L).
                request(MediaType.APPLICATION_JSON).
                get();
                //post(Entity.entity(book, MediaType.APPLICATION_JSON));
        response.bufferEntity();
        
        logResponse("shouldGETBook", response);
        assertEquals(book, response.readEntity(Book.class));
    }
    
    
    @Test
    @InSequence(3)
    @RunAsClient
    public void shouldReturnAValidationErrorWhenCreatingABook(@ArquillianResource final URL baseURL) {
        System.out.println("base: " + baseURL);
        
        final Book invalidBook = Book.of(2L, "s");
        
        final Client client = 
                ClientBuilder.newBuilder().
                              register(JacksonJsonProvider.class).build();
        final Response response = client.target(baseURL + "resources/books").
                                   request(MediaType.APPLICATION_JSON).
                                   header("Accept-Language", "pt").
                                   post(Entity.entity(invalidBook, MediaType.APPLICATION_JSON));
        response.bufferEntity();
        
    
        logResponse("shouldReturnAValidationErrorWhenCreatingABook", response);
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
    }
    
    private void logResponse(final String method, final Response response) {
        final StringBuilder builder = new StringBuilder(method).append("\n");
        builder.append("Response: ").append(response).append("\n");
        builder.append("Entity: ").append(response.readEntity(String.class)).append("\n");
        LOGGER.info(builder.toString());
    }


}
