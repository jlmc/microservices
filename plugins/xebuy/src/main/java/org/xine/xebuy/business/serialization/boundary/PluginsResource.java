package org.xine.xebuy.business.serialization.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("plugins")
@Produces(MediaType.TEXT_PLAIN)
public class PluginsResource {

	@Inject
	Plugins tester;

	@GET
	public String modules() {
		return tester.discoverPlugins();
	}

}
