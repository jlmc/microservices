package org.xine.paginator.business.tracing.boundary;

import java.util.logging.Logger;
import javax.inject.Inject;

public class LoggerInjectionSupport {

    @Inject
    Logger logger;

    public Logger getLogger() {
        return logger;
    }

}
