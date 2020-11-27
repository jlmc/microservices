package io.github.jlmc.bookshelf.core.auditing;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.lang.reflect.Method;
import java.util.Arrays;

@Auditable
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 2)
public class Auditor {

    @AroundInvoke
    public Object audit(final InvocationContext context) throws Exception {
        final Method method = context.getMethod();
        final Object target = context.getTarget();
        final Object[] params = context.getParameters();

        final Auditable annotation = method.getAnnotation(Auditable.class);

        System.out.println(String.format("Audite the method: '%s' " + "of the object: '%s' " + "with the params: '%s' -> [%s]",
                method,
                target,
                Arrays.toString(params),
                annotation.text()));

        return context.proceed();
    }
}
