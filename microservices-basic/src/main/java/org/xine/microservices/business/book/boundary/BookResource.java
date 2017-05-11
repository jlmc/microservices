package org.xine.microservices.business.book.boundary;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.xine.microservices.business.book.entity.Book;


@Path("book")
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class BookResource {

	@Inject
	Books service;

	@Inject
	java.util.logging.Logger logger;

	
	// @Path("json")
	// @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	// @Wrapped(element = "books")
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response list() {

		this.logger.info("get the List...");

		final org.xine.microservices.business.book.entity.Books wrapper = new org.xine.microservices.business.book.entity.Books();
		wrapper.addBooks(this.service.search());
		return Response.ok(wrapper).build();
	}

	@GET
	@Path("xml")
	@Produces(MediaType.APPLICATION_XML)
	@Wrapped(element = "books")
	public List<Book> listxml() {

		this.logger.info("get the List XML...");

		// public List<Book> search() {
		final List<Book> search = this.service.search();

		/** version I */
		// final org.xine.microservices.business.book.entity.Books wrapper = new
		// org.xine.microservices.business.book.entity.Books();
		// wrapper.addBooks(search);
		// return Response.ok(wrapper).build();

		//
		// return Response.ok(search).build();
		return search;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response read(@PathParam("id") final Integer id, @Context final UriInfo uriInfo) {
		this.logger.info("read the ID : " + id);

		try {

			final Book result = this.service.search(Long.valueOf(id));

			if (result == null) {
				return Response.status(Status.NOT_FOUND).build();
			}

			return Response.ok(result).build();

		} catch (final Exception e) {
			return Response.serverError().build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createBook(final Book book) {

		this.logger.info("POST Request: " + book);

		try {
			return Response.ok(this.service.save(book)).build();
		} catch (final Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateBook(final Book book) {
		this.logger.info("PUT Request: " + book);

		try {
			return Response.ok(this.service.save(book)).build();
		} catch (final Exception e) {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}


}
