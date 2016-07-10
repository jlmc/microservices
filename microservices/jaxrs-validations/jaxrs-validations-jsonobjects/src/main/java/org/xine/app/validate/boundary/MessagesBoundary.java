package org.xine.app.validate.boundary;

import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.xine.app.validate.ValidMessage;

@Path("messages")
public class MessagesBoundary {

	@POST
	// @Consumes(MediaType.APPLICATION_JSON)
	public Response save(@ValidMessage(expected = "duke") JsonObject input) {
		System.out.println("input = " + input);
		return Response.ok(input).build();
	}

}
