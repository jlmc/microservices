package org.xine.weather.business.horinca.boundary;

import java.io.IOException;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("horinca")
public class SpiritsResource {

    @GET
    public JsonObject get() {
        return Json.createObjectBuilder().
                add("name", "dracula").
                add("strength", 42).
                build();
    }

    @GET
    @Path("all")
    public JsonArray getAll() throws IOException {
        try (InputStream stream = this.getClass().getResourceAsStream("/test.json")) {
            return Json.createReader(stream).readArray();
        }
    }
}
