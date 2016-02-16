An annotation-based configuration requires recompilation and redeployment of the application upon any change and is suitable only for stable and never-changing schedules.However, watchdogs and timers for maintenance tasks can be realized as static, auto-registered timers.

To register a timer at runtime, you will have to use an instance of the <b>javax.ejb.SchedulerExpression</b> instead of <b>javax.ejb.Scheduler</b> annotation. The timer has to be explicitly registered with the injected <b>javax.ejb.TimerService#createCalendarTimer</b> method. The return value of the createCalendarMethod is an instance of <b>javax.ejb.Timer</b> that represents the configured timer and can be used for cancellation. 


<b>ReferrerScavengerTimer @Singletion</b>