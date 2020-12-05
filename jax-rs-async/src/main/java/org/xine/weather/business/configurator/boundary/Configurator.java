package org.xine.weather.business.configurator.boundary;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

public class Configurator {

    @Produces
    public String configure(InjectionPoint ip) {
        final String clazzName = ip.getMember().getDeclaringClass().getName();
        final String fieldName = ip.getMember().getName();

        return clazzName + " -> " + fieldName;
    }
}
