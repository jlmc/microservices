package org.xine.business.reports.control;

import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;

@Singleton
@Startup
public class ReportSchedulerLaucher {

	// @Resource(lookup="java:comp/DefaultManagedExecutorService")
	// ManagedExecutorService executor;

	// InitialContext ctx = new InitialContext();
	// ManagedScheduledExecutorService executor
	// =(ManagedScheduledExecutorService)ctx.lookup("java:comp/DefaultManagedScheduledExecutorService");

	@Resource(lookup = "java:comp/DefaultManagedScheduledExecutorService")
	ManagedScheduledExecutorService executor;

}
