package org.xine.microservices.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class BookClient {

    // private static final String HTTP_LOCALHOST_SUBJECTS =
    // "http://localhost:8080/Qtime-table-server-rest/resources";
    // http://localhost:8080/microservices-basic/resources/book
    private static final String HTTP_LOCALHOST_SUBJECTS = "http://localhost:8080/microservices-basic/resources";

    public static void main(String[] args) {
        testList();

        // read();

    }

    private static void read() {
        final Client client = ClientBuilder.newClient();

        final WebTarget target = client.target(HTTP_LOCALHOST_SUBJECTS);
        final WebTarget path = target.path("book/{id}");
        final WebTarget bookId = path.resolveTemplate("id", "1");

        final String invocation = bookId.request(MediaType.APPLICATION_JSON).get(String.class);

        // final Subject subject = invocation.get(OBject.class);

        System.out.println(invocation);
    }


    public static void testList() {
        // fail("Not yet implemented");

        final Client client = ClientBuilder.newClient();
        final String result = client.target(HTTP_LOCALHOST_SUBJECTS).path("book").request(MediaType.APPLICATION_XML)
                .get(String.class);

        System.out.println(result);
    }

}
