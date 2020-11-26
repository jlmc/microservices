package org.xine.extendable.business.newsletter.boundary;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import java.io.Serializable;

@Singleton
@Startup
public class Newsletter {

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialization() {

        // this can be build using configurations
        // Attention, The order of the cast matter
        {
            final Serializable sendSimpleNewsletterMethod = (Runnable & Serializable) this::sendSimpleNewsletter;

            timerService.createCalendarTimer(new ScheduleExpression().hour(0).minute(5).dayOfWeek(5),
                    new TimerConfig(sendSimpleNewsletterMethod, false));
        }

        {
            final Serializable sendCrismanNewsletterMethod = (Runnable & Serializable) this::sendCrismanNewsletter;
            timerService.createCalendarTimer(new ScheduleExpression().minute("*/5"),
                    new TimerConfig(sendCrismanNewsletterMethod, false));
        }

        // and can be many mores...
    }

    @Timeout
    public void execute(Timer timer) {
        System.out.println("On the time out");
        final Serializable info = timer.getInfo();
        final Runnable method = (Runnable) info;

        method.run();
    }

    private void sendCrismanNewsletter() {
        System.out.println("sendSimpleNewsletter");
    }

    private void sendSimpleNewsletter() {
        System.out.println("Sending simple news letter");
    }


}
