package org.xine.javax.rs.validation.books.boundary;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.xine.javax.rs.validation.books.entity.Book;

@Stateless
@Path("books")
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class BookResource {

	private static Set<Book> books = Collections.synchronizedSet(new HashSet<>());

	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response create(

			@FormParam("id") 
			@NotNull(message = "{book.id.notnull}")
			@Pattern(regexp = "[0-9]+", message="{book.id.pattern}")
			String id,
			
			@FormParam("title") 
			@Size(min = 3, max = 10, message = "{book.title.size}")
			String title, 
			
			@Context UriInfo info) {

		final Book book = Book.of(Long.valueOf(id), title);
		books.add(book);
		final URI uri = info.getAbsolutePathBuilder().path("/" + book.getId()).build();
		return Response.created(uri).entity(book).build();
	}

	@POST
	public Response post(@Valid Book book, @Context UriInfo info) {
		books.add(book);
		final URI uri = info.getAbsolutePathBuilder().path("/" + book.getId()).build();
		return Response.created(uri).entity(book).build();
	}

	@GET
	@Path("{id}")
	public Response get(@PathParam("id") Long id) {
		return books.stream().
				filter(b -> id.equals(b.getId())).findAny().
				map(ob -> Response.ok(ob).build()).orElse(Response.status(Status.NOT_FOUND).build());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get() {
		final Collection<Book> values = new ArrayList<Book>(books);
		final GenericEntity<Collection<Book>> genericEntity = new GenericEntity<Collection<Book>>(values) {};
		return Response.ok(genericEntity).build();
	}

}
