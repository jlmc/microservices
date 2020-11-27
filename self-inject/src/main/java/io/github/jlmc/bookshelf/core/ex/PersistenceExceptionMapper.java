package io.github.jlmc.bookshelf.core.ex;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class PersistenceExceptionMapper implements ExceptionMapper<PersistenceException> {

    @Override
    public Response toResponse(PersistenceException exception) {
        if (exception instanceof EntityNotFoundException || exception instanceof NoResultException ) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("code", "ERR-4711");
            response.put("type", "DATABASE");
            response.put("message", exception.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(response).type(MediaType.APPLICATION_JSON).build();
        }
    }
}
