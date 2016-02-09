package org.xine.business.registations.boundary;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class LoggingInterceptor {

	@Resource
	SessionContext sc;

	@AroundInvoke
	public Object log(final InvocationContext ic) throws Exception {
		System.out.println("---------- " + ic.getMethod() + " " + this.sc.getCallerPrincipal());

		return ic.proceed();
	}

}
