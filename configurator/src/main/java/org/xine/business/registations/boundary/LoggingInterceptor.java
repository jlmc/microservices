package org.xine.business.registations.boundary;

import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingInterceptor {

    @Inject
    Logger logger;

    @Resource
    SessionContext sc;

    @AroundInvoke
    public Object log(final InvocationContext ic) throws Exception {
        this.logger.info("---------- " + ic.getMethod() + " " + this.sc.getCallerPrincipal());

        return ic.proceed();
    }

}
