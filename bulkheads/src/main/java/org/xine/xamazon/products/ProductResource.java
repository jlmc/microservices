package org.xine.xamazon.products;

import org.xine.xamazon.bulk.boundary.Dedicated;

import javax.inject.Inject;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Path("products")
@Produces({MediaType.APPLICATION_JSON})
public class ProductResource {

    @Inject
    @Dedicated("heavyExecutorService")
    ExecutorService heavyExecutorService;

    @Inject
    @Dedicated("lightExecutorService")
    ExecutorService lightExecutorService;

    @GET
    @Path("light")
    public void light(@Suspended AsyncResponse response) {
        Supplier<JsonArray> supplier = this::lightProducts;
        Consumer<JsonArray> consumer = response::resume;
        CompletableFuture.supplyAsync(supplier, this.heavyExecutorService)
                .thenAccept(consumer);
    }

    @GET
    @Path("heavy")
    public void heavy(@Suspended AsyncResponse response) {
        Supplier<JsonArray> supplier = this::heavyProducts;
        Consumer<JsonArray> consumer = response::resume;
        CompletableFuture.supplyAsync(supplier, this.heavyExecutorService)
                .thenAccept(consumer);
    }

    private JsonArray heavyProducts() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.lightProducts();
    }

    private JsonArray lightProducts() {
        return Json.createArrayBuilder()
                .add(Json.createObjectBuilder().add("name", "samsung").add("price", BigDecimal.TEN))
                .add(Json.createObjectBuilder().add("name", "mouse").add("price", BigDecimal.ONE))
                .build();
    }
}
