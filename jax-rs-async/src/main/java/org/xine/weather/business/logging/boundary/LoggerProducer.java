package org.xine.weather.business.logging.boundary;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerProducer {

	@Produces
	// public Consumer<String> expose(InjectionPoint ip) {
	public Log expose(InjectionPoint ip) {
		final String clazzName = ip.getMember().getDeclaringClass().getName();

		return Logger.getLogger(clazzName)::info;
	}

}
