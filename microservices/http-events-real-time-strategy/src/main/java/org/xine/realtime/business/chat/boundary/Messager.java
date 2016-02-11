package org.xine.realtime.business.chat.boundary;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("messager")
@Produces(MediaType.TEXT_PLAIN)
public class Messager {

	@Inject
	Event<String> pull;

	@GET
	public Response message() {
		this.pull.fire("hello world");
		return Response.ok().build();
	}

}
