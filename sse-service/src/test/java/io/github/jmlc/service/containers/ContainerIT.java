package io.github.jmlc.service.containers;

import org.glassfish.jersey.jsonb.JsonBindingFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


@Testcontainers
public class ContainerIT {

    public static final String TARGET_SSE_SERVICE_WAR = "target/sse-service.war";

    @Container
    private GenericContainer<?> container =
            new GenericContainer<>(new ImageFromDockerfile()
                .withFileFromFile("Dockerfile", new File(basePath(), "Dockerfile"))
                .withFileFromFile(TARGET_SSE_SERVICE_WAR, new File(basePath(), TARGET_SSE_SERVICE_WAR)))
                //.waitingFor(Wait.forHttp("/resources/ping"))
                //.waitingFor(Wait.forLogMessage("WFLYSRV0025: WildFly Full 20.0.1.Final", 1))
                .waitingFor(Wait.forLogMessage("^.*WFLYSRV0025.*$", 1))
                .withLogConsumer(new Slf4jLogConsumer(LoggerFactory.getLogger(ContainerIT.class)))
                .withExposedPorts(8080)
                .withExtraHost("localhost", "127.0.0.1");

    private Client client;
    private WebTarget resources;

    @BeforeEach
    public void setUp() {
        client = ClientBuilder.newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .register(JsonBindingFeature.class)
                .build();


        final String hosts = container.getContainerIpAddress();
        final Integer mappedPort = container.getMappedPort(8080);

        String uri = String.format("http://%s:%s/resources", hosts, mappedPort);
        this.resources = client.target(uri);
    }

    @AfterEach
    void tearDown() {
        client.close();
    }

    @Test
    void should_perform_get_ping_with_success() {
        //@formatter:off
        String responseMessage =
                resources.path("/ping")
                         .request()
                         .get()
                         .readEntity(String.class);
        //@formatter:on

        System.out.println(responseMessage);
        Assertions.assertEquals("Enjoy Jakarta EE 8 with MicroProfile 3+!", responseMessage);
    }

    private static String basePath() {
        URL resource = ContainerIT.class.getResource("/");
        File file;
        try {
            file = new File(resource.toURI()).getParentFile().getParentFile();
        } catch (URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        return file.getAbsolutePath();
    }
}
