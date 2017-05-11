package org.xine.business.audit.boundary;

import java.lang.reflect.Method;

import javax.annotation.Priority;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Auditable
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 2)
public class Auditor {

	@AroundInvoke
	public Object audit(final InvocationContext context) throws Exception {
		final Method method = context.getMethod();
		final Object target = context.getTarget();
		final Object[] params = context.getParameters();

		System.out.println(String.format("Audite the method: '%s' " + "of the object: '%s' " + "with the params: '%s'",
				method,
				target,
				params));

		return context.proceed();
	}
}
