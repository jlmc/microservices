package org.xine.business.scanvenger.control;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

@Singleton
@Interceptors(PersonmanceAuditor.class)
public class ReferrerScavengerTimer {

    @Inject
    private Instance<String> scanvengerRuns;

    @Resource
    TimerService timerService;

    @Inject
    ReferredScanvenger referredScanvenger;

    public void initializeTimer() {
        final ScheduleExpression expression = new ScheduleExpression();
        expression.dayOfWeek(this.scanvengerRuns.get());
        final TimerConfig timerConfig = new TimerConfig();
        timerConfig.setPersistent(false);
        this.timerService.createCalendarTimer(expression, timerConfig);
    }

    @Timeout
    public void garbageCollectorReferrers() {
        this.referredScanvenger.removeInactiveReferrers();
    }
}
