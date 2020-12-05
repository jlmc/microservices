package org.xine.extendable.business.newsletter.boundary;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Singleton
@Startup
public class Newsletter {

    @Resource
    TimerService timerService;

    @PostConstruct
    public void initialization() {
        // this can be build using configurations
        final ScheduleExpression scheduleExpression = new ScheduleExpression().hour(0).minute(5).dayOfWeek("5–1");
        final TimerConfig timerConfig = new TimerConfig("simpleNewsletter", false);

        timerService.createCalendarTimer(scheduleExpression, timerConfig);

        // this can be build using configurations
        final ScheduleExpression scheduleExpressionCrisman = new ScheduleExpression().dayOfMonth(25).month(12).hour(0);
        final TimerConfig timerConfigCrisman = new TimerConfig("crismanNewsletter", false);
        timerService.createCalendarTimer(scheduleExpression, timerConfigCrisman);

        // and can be many mores...
    }

    @Timeout
    public void execute(Timer timer) {
        final Serializable methodName = timer.getInfo();

        switch (String.valueOf(methodName)) {
        case "simpleNewsletter":
            sendSimpleNewsletter();
            break;
        case "crismanNewsletter":
            sendCrismanNewsletter();
            break;

        default:
            break;
        }
    }

    private void sendCrismanNewsletter() {
        System.out.println("sendSimpleNewsletter");
    }

    private void sendSimpleNewsletter() {
        System.out.println("Sending simple news letter");

    }


}
