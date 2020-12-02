package io.github.jmlc.service.events;

import org.junit.jupiter.api.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
class SseClientIT {

    private static final Logger LOGGER = Logger.getAnonymousLogger();

    private Client client;
    private WebTarget webTarget;
    private ScheduledExecutorService executorService;

    @BeforeEach
    void setUp() {
        this.client =
                ClientBuilder.newBuilder()
                        .connectTimeout(5, TimeUnit.SECONDS)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .build();

        webTarget =
                client.target("http://localhost:8080")
                        .path("/resources/events");

        this.executorService = Executors.newSingleThreadScheduledExecutor();
    }

    @AfterEach
    void tearDown() {
        client.close();
        executorService.shutdown();
    }

    @Test
    void receiveSse() throws InterruptedException {
        executorService.scheduleWithFixedDelay(
                this::sendMessage,
                250L,
                500L,
                TimeUnit.MILLISECONDS);

        try (SseEventSource eventSource = SseEventSource.target(webTarget).build()) {
            eventSource.register(this::receiveSse);
            eventSource.open();

            TimeUnit.SECONDS.sleep(10_000);
        }
    }

    private void receiveSse(InboundSseEvent inboundSseEvent) {
        LOGGER.log(Level.INFO, "Recieved event {0} with data {1}.",
                new Object[]{inboundSseEvent.getName(), inboundSseEvent.readData()});
    }

    private void sendMessage() {
        webTarget.request()
                .post(Entity.entity("Hello SSE JAX-RS client.", MediaType.TEXT_PLAIN_TYPE));
    }
}