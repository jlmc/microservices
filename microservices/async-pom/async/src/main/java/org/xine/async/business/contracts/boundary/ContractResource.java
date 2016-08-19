package org.xine.async.business.contracts.boundary;

import java.net.URI;
import java.util.Locale;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.xine.async.business.contracts.entity.Contract;

@Path("contracts")
@Produces(MediaType.APPLICATION_JSON)
public class ContractResource {

	@Inject
	ContractManager manager;

	@GET
	@Path("{id : \\d+}")
	public Response get(@PathParam("id") final Long id) {
		return Response.ok(this.manager.get(id)).build();
	}

	@POST
	public Response post(@Valid Contract book, @Context final UriInfo info,
			@HeaderParam("Accept-Language") final Locale locale) {

		final Contract createdContract = this.manager.create(book);

		final URI uri = info.getAbsolutePathBuilder().path("/" + createdContract.getId()).build();
		return Response.created(uri).entity(createdContract).build();
	}

}
