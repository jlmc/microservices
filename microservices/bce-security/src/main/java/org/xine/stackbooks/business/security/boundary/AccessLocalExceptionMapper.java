package org.xine.stackbooks.business.security.boundary;

import javax.ejb.AccessLocalException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

//@javax.ws.rs.ext.Provider
public class AccessLocalExceptionMapper implements ExceptionMapper<AccessLocalException> {

	@Context
	HttpServletRequest request;

	@Override
	public Response toResponse(AccessLocalException exception) {

		try {
			this.request.logout();
		} catch (final ServletException e) {
		}

		return Response.status(Response.Status.UNAUTHORIZED).build();

	}

}
