package org.xine.business.scanvenger.control;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.xine.business.scanvenger.control.ReferrerScavengerTimer;

@Singleton
@Startup
public class ScavengerStarted {

    @Resource
    TimerService timerService;

    @Inject
    ReferrerScavengerTimer referrerScavengerTimer;

    private final long startDelay = 60 * 1000;
    private Timer timer;

    @PostConstruct
    public void initiliaseTimer() {
        final TimerConfig config = new TimerConfig();
        config.setPersistent(false);
        this.timer = this.timerService.createSingleActionTimer(this.startDelay, config);
    }

    @Timeout
    public void startScanvenger() {
        this.referrerScavengerTimer.initializeTimer();
    }

    public void cleanupTimer() {
        this.timer.cancel();
    }

}
