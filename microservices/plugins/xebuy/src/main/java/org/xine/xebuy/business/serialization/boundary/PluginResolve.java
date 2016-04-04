package org.xine.xebuy.business.serialization.boundary;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.xine.xebuy.business.plugin.serializer.Serialization;
import org.xine.xebuy.business.plugin.serializer.Serialization.PlanType;
import org.xine.xebuy.business.plugin.serializer.Serializer;

@Stateless
@Path("allplugins")
@Produces(MediaType.TEXT_PLAIN)
public class PluginResolve {

	@Inject
	@Any
	Instance<Serializer> plugins;

	@Inject
	@Serialization(plantype = PlanType.OPTIMIZED)
	Serializer opt;

	@Inject
	@Serialization(plantype = PlanType.DEFAULT)
	Serializer normal;

	@GET
	public String modules() {
		final String optimazerPlugin = opt.getClass().toString();
		final String normalPlugin =  normal.getClass().toString();
		
		return "OP: " + optimazerPlugin + " < - > DEF: " + normalPlugin;
	}
}
