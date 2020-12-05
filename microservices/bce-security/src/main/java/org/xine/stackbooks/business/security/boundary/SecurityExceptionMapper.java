package org.xine.stackbooks.business.security.boundary;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

//@javax.ws.rs.ext.Provider
public class SecurityExceptionMapper implements ExceptionMapper<SecurityException> {

    @Context
    HttpServletRequest request;

    @Override
    public Response toResponse(SecurityException exception) {
        try {
            this.request.logout();
        } catch (final Exception e) {
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
