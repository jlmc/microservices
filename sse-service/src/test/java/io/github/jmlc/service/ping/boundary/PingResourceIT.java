package io.github.jmlc.service.ping.boundary;

import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.util.concurrent.TimeUnit;

class PingResourceIT {

    private Client client;
    private WebTarget webTarget;

    @BeforeEach
    void setUp() {
        this.client =
                ClientBuilder.newBuilder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .register(JsonBindingFeature.class)
                        .build();

        webTarget =
                client.target("http://localhost:8080")
                        .path("/resources/ping");
    }

    @AfterEach
    void tearDown() {
        client.close();
    }

    @Test
    void connect() {
        String responseMessage =
                webTarget.request()
                        .get()
                        .readEntity(String.class);

        System.out.println(responseMessage);
    }
}