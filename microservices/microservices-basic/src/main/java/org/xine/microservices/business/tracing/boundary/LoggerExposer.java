package org.xine.microservices.business.tracing.boundary;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerExposer implements Serializable {

	@Produces
	@Dependent
	public Logger exposer(InjectionPoint ip) {
		final String loggerName = ip.getMember().getDeclaringClass().getName();
		return Logger.getLogger(loggerName);
	}

}

