package org.xine.extendable.business.microscopes.method;

import java.lang.reflect.Method;

import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.xine.extendable.business.microscopes.ScopeContext;

@Interceptor
@MethodScopeEnabled
public class MethodScopedInterceptor {
	@Inject
	private BeanManager beanManager;

	@AroundInvoke
	public Object invoke(InvocationContext invocation) throws Exception {
		final ScopeContext<Method> context = (ScopeContext<Method>) beanManager.getContext(MethodScoped.class);

		final Method previous = context.enter(invocation.getMethod());
		try {
			return invocation.proceed();
		} finally {
			context.exit(previous);
		}
	}
}
