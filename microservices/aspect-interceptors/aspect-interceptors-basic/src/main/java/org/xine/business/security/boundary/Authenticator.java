package org.xine.business.security.boundary;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Secure
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
public class Authenticator {

    @AroundInvoke
    public Object authentic(final InvocationContext context) throws Exception {
        final Method method = context.getMethod();
        final Object target = context.getTarget();
        final Object[] params = context.getParameters();

        System.out.println(String.format("Authenticator the method: '%s' " + "of the object: '%s' " + "with the params: '%s'",
                method, target, params));

        return context.proceed();
    }

}
