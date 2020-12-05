package org.xine.weather.business.forecast.boundary;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.airhacks.porcupine.execution.boundary.Dedicated;

@Path("forecasts")
public class WeatherForecastResource {

    @Inject
    WeatherForecaster wf;

    @Resource
    ManagedExecutorService mes;

    @Inject
    @Dedicated
    ExecutorService dedicatedMes;

    @Context
    UriInfo uriInfo;

    public Response withLocationHeader() {
        final URI uri = uriInfo.getAbsolutePathBuilder().path("/strornId").build();
        return Response.created(uri).build();
    }

    @GET
    public void all(@Suspended AsyncResponse response) {
        final Consumer<Object> consumer = response::resume;
        final Supplier<Object> supplier = this::forecast;
        // so we ca use ManagedExecutorService
        // CompletableFuture.supplyAsync(supplier, mes).thenAccept(consumer);
        CompletableFuture.supplyAsync(supplier, dedicatedMes).thenAccept(consumer);
    }

    /*
    @GET
    public void all(@Suspended AsyncResponse response) {
        final Consumer<Object> consumer = response::resume;
        final Supplier<Object> supplier = this::forecast;
        // the only problems this, is that CompletableFuture execute in a fork
        // join, and that is not very rebut
        CompletableFuture.supplyAsync(supplier).thenAccept(consumer);
    }
    */

    public JsonArray forecast() {
        final JsonArray result = Json.createArrayBuilder().add(wf.all()).add("sun").build();
        return result;
    }

    // @GET
    // public JsonArray all() {
    // return Json.createArrayBuilder().add("clouds").add("sun").build();
    // }

}
