package org.xine.business.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("customscope")
@Produces(MediaType.TEXT_PLAIN)
public class InjectionResource {

	@Inject
	InjectionBoundary tester;

	@GET
	@Path("injection")
	public String inject() {
		return this.tester.invokeService();
	}

}
