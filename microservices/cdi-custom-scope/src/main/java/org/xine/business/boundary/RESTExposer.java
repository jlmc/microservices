package org.xine.business.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xine.business.control.AnotherControl;
import org.xine.business.control.Control;
import org.xine.business.control.DependentControl;
import org.xine.business.scopes.ManualManagedContext;

@Stateless
@Path("customscope")
@Produces(MediaType.TEXT_PLAIN)
public class RESTExposer {

    @Inject
    InjectionBoundary tester;

    @GET
    @Path("injection")
    public String inject() {
        return tester.invokeService();
    }

    @GET
    @Path("beancontext")
    public String beanContext() {
        return ManualManagedContext.getInstance().getBeanContext().toString();
    }

    @GET
    @Path("sameinstance")
    public String sameInstance() {
        return tester.sameInstance() ? "+" : "-";
    }

    @GET
    @Path("samelazyinstance")
    public String sameLazyInstance() {
        return tester.lazyFetch() ? "+" : "-";
    }

    @GET
    @Path("shutdown")
    public String shutdown() {
        ManualManagedContext.getInstance().shutdown();
        return "+";
    }

    @GET
    @Path("injectedAsString")
    public String injectedAsString() {
        return tester.injectedAsString();
    }
    
    @GET
    @Path("instanceCount")
    public String instanceCount(){
        return AnotherControl.INSTANCE_COUNTER.intValue() + "" + Control.INSTANCE_COUNTER.intValue() + "" + DependentControl.INSTANCE_COUNTER.intValue();
    }
}
