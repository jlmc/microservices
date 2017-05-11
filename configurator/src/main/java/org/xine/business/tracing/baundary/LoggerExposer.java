package org.xine.business.tracing.baundary;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

@ApplicationScoped
public class LoggerExposer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Produces
	@Dependent
	public Logger exposer(final InjectionPoint ip) {
		final String loggerName = ip.getMember().getDeclaringClass().getName();
		return Logger.getLogger(loggerName);
	}

}

