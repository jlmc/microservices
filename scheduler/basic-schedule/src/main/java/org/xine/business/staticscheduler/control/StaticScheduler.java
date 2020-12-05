package org.xine.business.staticscheduler.control;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.ejb.Schedule;
import javax.ejb.Singleton;

@Singleton
public class StaticScheduler {

    @Schedule(hour = "*", minute = "*", second = "*/5")
    public void execute() {
        System.out.println(
                "executing: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
    }
}
