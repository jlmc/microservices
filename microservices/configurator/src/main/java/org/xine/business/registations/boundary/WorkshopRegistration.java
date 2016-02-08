package org.xine.business.registations.boundary;

import java.net.URI;
import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.xine.business.configuration.boundary.Configurable;
import org.xine.business.registations.entity.Workshop;

@Stateless
// @TransactionAttribute(TransactionAttributeType.REQUIRED)
public class WorkshopRegistration {

	@PersistenceContext // (unitName="NovaShopPU")
	EntityManager em;

	@Inject
	@Configurable("maxNumberOfRegistrations")
	String maxNumberOfRegistrations;

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response register(final Workshop workshop) {
		this.em.persist(workshop);
		// registrationService.fire(workshop);
		final URI create = URI.create(String.valueOf(workshop.getId()));
		// sc.setRollbackOnly();
		return Response.created(create).build();

	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Workshop byId(@PathParam("id") final int id) {
		return new Workshop("withId", id);
	}

	public Date getDate() {
		return new Date();
	}

}
