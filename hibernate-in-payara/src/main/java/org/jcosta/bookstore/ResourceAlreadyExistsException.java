package org.jcosta.bookstore;

import javax.ejb.ApplicationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@ApplicationException(rollback = true)
public class ResourceAlreadyExistsException extends WebApplicationException {

    public Response getResponse() {
        return Response.status(Response.Status.CONFLICT).entity(Error.of("xv", "t")).build();
    }




}
