package io.github.jmlc.service.events;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

@ApplicationScoped
@Path("/broadcast")
public class BroadcastResource {

    @Context
    private Sse sse;

    private SseBroadcaster sseBroadcaster;

    @PostConstruct
    public void initialize() {
        sseBroadcaster = sse.newBroadcaster();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public void fetch(@Context SseEventSink sseEventSink) {
        sseBroadcaster.register(sseEventSink);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response broadcast(@FormParam("message") String message) {
        OutboundSseEvent broadcastEvent = sse.newEvent("message", message);
        sseBroadcaster.broadcast(broadcastEvent);
        return Response.noContent().build();
    }
}
