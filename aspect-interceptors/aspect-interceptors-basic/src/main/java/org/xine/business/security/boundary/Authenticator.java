package org.xine.business.security.boundary;

import java.lang.reflect.Method;
import java.util.Arrays;

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

        System.out.printf("Authenticator the method: '%s' " + "of the object: '%s' " + "with the params: '%s'%n \n",
                method, target, Arrays.toString(params));

        return context.proceed();
    }

}
