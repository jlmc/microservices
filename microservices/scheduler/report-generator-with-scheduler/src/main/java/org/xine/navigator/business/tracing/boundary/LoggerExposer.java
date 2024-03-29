package org.xine.navigator.business.tracing.boundary;

import java.util.logging.Logger;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class LoggerExposer {

    @Produces
    public Logger expose(final InjectionPoint ip) {
        final String loggerName = ip.getMember().getDeclaringClass().getName();
        return Logger.getLogger(loggerName);
    }
}
