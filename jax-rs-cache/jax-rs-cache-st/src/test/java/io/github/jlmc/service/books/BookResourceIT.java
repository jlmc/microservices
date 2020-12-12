package io.github.jlmc.service.books;

import io.github.jlmc.service.config.Configurations;
import io.github.jlmc.service.filters.client.ClientCacheResponseFilter;
import io.github.jlmc.service.filters.client.ClientCacheRequestFilter;
import io.github.jlmc.service.filters.client.cache.Cache;
import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookResourceIT {

    private Cache cache;

    private Client client;
    private WebTarget webTarget;

    @BeforeEach
    void setUp() {
        this.cache = new Cache();

        this.client =
                ClientBuilder.newBuilder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .register(JsonBindingFeature.class)
                        .register(new ClientCacheResponseFilter(cache))
                        .register(new ClientCacheRequestFilter(cache))
                        .build();

        webTarget =
                //client.target("http://localhost:8080")
                client.target(Configurations.getAppResourcesUri())
                        .path("/books");
    }

    @Test
    @Order(1)
    void getBooks() {
        Response response =
                webTarget.request()
                         .accept(MediaType.APPLICATION_JSON)
                         .get();

        assertEquals(200, response.getStatus());
        JsonArray jsonValues = response.readEntity(JsonArray.class);

        System.out.println(response.getHeaders());
        System.out.println(jsonValues);
    }

    @Test
    @Order(2)
    void getBooksById() {
        Response response1 = getBookById();

        assertEquals(200, response1.getStatus());
        System.out.println(response1.getStatus());
        response1.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println(response1.readEntity(JsonObject.class));

        System.out.println("--------");

        Response response2 = getBookById();
        assertEquals(Response.Status.OK.getStatusCode(), response2.getStatus());

        System.out.println(response2.getStatus());
        response2.getHeaders().forEach((k, v) -> System.out.println(k + ": " + v));
        System.out.println(response2.readEntity(JsonObject.class));
    }

    private Response getBookById() {
        return webTarget
                .path("/{id}")
                .resolveTemplate("id", 999)
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
    }
}
