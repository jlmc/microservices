package io.github.jmlc.service.events;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseEventSink;
import java.time.Instant;

/**
 * Only one Client will receive the notifications... Unless we cache the SseEventSink on the GET method.
 */
@Path("/events")
@ApplicationScoped
public class EventsResource {

    private SseEventSink eventSink;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void openEventStream(@Context final SseEventSink eventSink) {
        //eventSinks.add(eventSink);
        this.eventSink = eventSink;
    }

    @DELETE
    public void closeEventStream() {
        final SseEventSink localSink = eventSink;

        if (localSink != null) {
            this.eventSink.close();
        }

        this.eventSink = null;
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    public void sendEvent(String message, @Context Sse sse) {
        final SseEventSink localSink = eventSink;
        if (localSink == null) {
            return;
        }

        /*
        // send simple event
        OutboundSseEvent event = sse.newEvent(message);
        localSink.send(event);

        // send simple string event
        OutboundSseEvent stringEvent = sse.newEvent("stringEvent", message + " From server.");
        localSink.send(stringEvent);

        // send primitive long event using builder
        OutboundSseEvent primitiveEvent = sse.newEventBuilder()
                .name("primitiveEvent")
                .data(System.currentTimeMillis()).build();
        localSink.send(primitiveEvent);
         */

        // send JSON-B marshalling to send event
        OutboundSseEvent jsonbEvent = sse.newEventBuilder()
                .name("jsonbEvent")
                .data(new JsonbSseEvent(message))
                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                .build();

        localSink.send(jsonbEvent);
    }

    @JsonbPropertyOrder({"time", "message"})
    public static class JsonbSseEvent {
        public Instant time;
        public String message;

        public JsonbSseEvent(String message) {
            this.time = Instant.now();
            this.message = message;
        }
    }
}
