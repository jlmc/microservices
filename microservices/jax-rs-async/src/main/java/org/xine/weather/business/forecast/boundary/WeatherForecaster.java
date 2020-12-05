package org.xine.weather.business.forecast.boundary;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.xine.weather.business.logging.boundary.Log;

@Stateless
public class WeatherForecaster {

    @Inject
    Log LOG;
    // Consumer<String> LOG;

    @Inject
    String password;

    public String all() {
        LOG.log("seems to work");
        //LOG.accept("seems to work");
        return "storms " + password;
    }
}
