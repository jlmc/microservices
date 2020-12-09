package io.github.jlmc.service.books.boundary;

import io.github.jlmc.chassis.validations.Validations;
import io.github.jlmc.service.books.control.Books;
import io.github.jlmc.service.books.entity.Book;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Tag(name = "Book http cache")

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookResources {

    @Inject
    Books books;

    @Context
    UriInfo uriInfo;

    @GET
    public Response all() {
        List<Book> all = books.all();

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(10);
        cacheControl.setPrivate(false);
        cacheControl.setNoTransform(true);

        return Response.ok(all).cacheControl(cacheControl).build();
    }

    @GET
    @Path("/{id: \\d+}")
    public Response findById(@PathParam("id") Integer id) {

        Book book = books.findById(id);

        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(10);
        cacheControl.setPrivate(false);
        cacheControl.setNoTransform(true);

        return Response.ok(book)
                .cacheControl(cacheControl)
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(
            @Valid
            @ConvertGroup(from = Default.class, to = Validations.Creation.class)
            Book book) {
        Book created = books.add(book);

        URI uri = uriInfo.getAbsolutePathBuilder().path("/{id}").build(created.getId());

        return Response.created(uri).entity(created).build();
    }
}
