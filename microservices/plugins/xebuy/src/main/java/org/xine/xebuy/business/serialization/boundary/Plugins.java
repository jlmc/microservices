package org.xine.xebuy.business.serialization.boundary;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.xine.xebuy.business.plugin.serializer.Serializer;

@Stateless
public class Plugins {

	@Inject
	@Any
	Instance<Serializer> plugins;

	public String discoverPlugins() {
		System.out.println("");
		String retVal = "";
		
		for (final Serializer plugin : plugins) {
			retVal += plugin.getClass().getName();
		}
		return retVal;
	}

}
