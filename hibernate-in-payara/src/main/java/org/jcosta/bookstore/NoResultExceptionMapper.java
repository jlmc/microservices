package org.jcosta.bookstore;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException> {

    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(NoResultException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Error.of("Book not Found", exception.getMessage()))
                .build();
    }
}
