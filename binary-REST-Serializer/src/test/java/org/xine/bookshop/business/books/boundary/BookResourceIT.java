package org.xine.bookshop.business.books.boundary;

import static org.junit.Assert.assertNotNull;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xine.bookshop.business.books.entity.Book;
import org.xine.bookshop.business.restserialization.control.CustomMediaType;
import org.xine.bookshop.business.restserialization.control.JavaDeserializer;
import org.xine.bookshop.business.restserialization.control.JavaSerializer;

public class BookResourceIT {

    private static final String RESOURCE_URI = "http://localhost:8080/binary-REST-Serializer/resources";

    private Client client;

    @Before
    public void init() {
        this.client = ClientBuilder.newClient();
        this.client.register(JavaSerializer.class)
        .register(JavaDeserializer.class);
    }

    @Test
    public void get() {
        final WebTarget target = this.client.target(RESOURCE_URI).path("book");
        final Book book = target.request(CustomMediaType.SERIALIZATION_JAVA).get(Book.class);

        Assert.assertNotNull(book);
    }

    @Test
    public void post() {
        final Response postResponse = this.client.target(RESOURCE_URI).path("book")
                .request(CustomMediaType.SERIALIZATION_JAVA)
                .post(Entity.entity(new Book(123L, "xyz"), CustomMediaType.SERIALIZATION_JAVA));
        assertNotNull(postResponse);

    }

}
