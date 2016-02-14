package org.xine.channels.business.configuration.boundary;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@Startup
@Singleton
public class Configurator {

	private final int interval = 5;
	private Map<String, String> configuration;

	@PostConstruct
	public void initialize() {
		this.configuration = new HashMap();
		this.configuration.put("location", "localhost:4848");
		this.configuration.put("jdbcPoolNames", "SamplePool");
		this.configuration.put("interval", "2");
		this.configuration.put("username", "");
		this.configuration.put("password", "");
		this.configuration.put("serverInstance", "server");
	}

	// @Produces
	// public String getString(final InjectionPoint ip) {
	// final String name = ip.getMember().getName();
	// return this.configuration.get(name);
	// }

	@Produces
	public int getInteger(final InjectionPoint ip) {
		return Integer.parseInt(getString(ip));
	}

	@Produces
	public boolean getBoolean(final InjectionPoint ip) {
		return Boolean.parseBoolean(getString(ip));
	}

	@Produces
	public String getString(final InjectionPoint ip) {
		final String name = ip.getMember().getName();
		return this.configuration.get(name);
	}

	@Produces
	public String[] getStringArray(final InjectionPoint ip) {
		return asArray(getString(ip));
	}

	private String[] asArray(final String value) {
		return value.split(",");
	}

	private int getValueAsInt(final String interval) {
		return Integer.parseInt(getValue(interval));
	}

	private String getValue(final String key) {
		return this.configuration.get(key);
	}

	private void setValue(final String key, final int interval) {
		setValue(key, String.valueOf(interval));
	}

	private void setValue(final String key, final boolean value) {
		setValue(key, String.valueOf(value));
	}

	private void setValue(final String key, final String value) {
		this.configuration.put(key, value);
	}
}
