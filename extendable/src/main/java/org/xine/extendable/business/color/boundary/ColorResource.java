package org.xine.extendable.business.color.boundary;

import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.xine.extendable.business.color.control.Count;
import org.xine.extendable.business.microscopes.method.MethodScopeEnabled;

//@Lock(READ)
@Singleton
@Path("/color")
@MethodScopeEnabled
public class ColorResource {
    @Inject
    private Count count;

    @GET
    @Path("/red")
    public String red() {
        return String.format("red, %s invocations", count.add());
    }

    @GET
    @Path("/green")
    public String green() {
        return String.format("green, %s invocations", count.add());
    }

    @GET
    @Path("/blue")
    public String blue() {
        return String.format("blue, %s invocations", count.add());
    }

}
