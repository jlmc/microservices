package org.xine.stackbooks.business.tracing.boundary;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerExposer {

	@Produces
	public Logger expose(InjectionPoint ip) {
		final String loggerName = ip.getMember().getDeclaringClass().getName();
		return Logger.getLogger(loggerName);
	}

}
