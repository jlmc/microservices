package org.xine.async.business;

import javax.persistence.NoResultException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoResultExceptionMapper implements ExceptionMapper<NoResultException> {

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(NoResultException exception) {
		return Response.status(Status.NOT_FOUND)
				.build();
	}

}
