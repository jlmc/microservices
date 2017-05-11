package org.xine.bookshop.business.books.boundary;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xine.bookshop.business.books.entity.Book;
import org.xine.bookshop.business.restserialization.control.CustomMediaType;

@Path("book")
@Stateless
public class BookResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, CustomMediaType.SERIALIZATION_JAVA })
	public Book getBook() {
		return new Book(1L, "my thisng Book");
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, CustomMediaType.SERIALIZATION_JAVA })
	public void saveBook(final Book book) {
		System.out.println(book);

	}

}
