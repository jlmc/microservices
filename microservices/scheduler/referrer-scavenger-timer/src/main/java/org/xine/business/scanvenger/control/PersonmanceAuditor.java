package org.xine.business.scanvenger.control;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.AroundTimeout;
import javax.interceptor.InvocationContext;

public class PersonmanceAuditor {

    @Inject
    Logger logger;

    @AroundTimeout
    @AroundInvoke
    public Object measurePerformance(final InvocationContext context) throws Exception {

        final String methodName = context.getMethod().toString();
        final long start = System.currentTimeMillis();

        try {
            return context.proceed();
        } catch (final Exception e) {
            this.logger.log(Level.SEVERE, "Some probleme", e);
            throw e;
        } finally {
            final long duration = System.currentTimeMillis() - start;
            this.logger.info("The duration was: " + duration + "mils to the method: " + methodName);
        }
    }

}
