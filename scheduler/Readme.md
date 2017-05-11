#Schedule
Realizes periodic and transactional execution of tasks. Scheduler can be realized as Control or Boundary and is similar to Configurator as Aspect pattern. 

##Problem 
Java EE Applications are not supposed to start or manager threads. without being able to use threads, the implementation of the timer and scheduler is impossible. Fortunately. Java EE 6 introduced a powerful solution for compile and runtime management.

##Forces
* A flexible  (cron-like) way to configure timers is needed .
* We have to execute business method periodically.
* Timer methods have to be executed in all-or-nothing transaction fashion.
* Timer need to be register automatically at the application server's start or deploy time.
* Manual timer management at runtime is also required.
* Timer configuration needs to survive server crash or restart.


## Solution 
EJB 3.1 comes with a configurable and powerful timer management solution. To Execute a method in a @Singleton , @Staleless, or messaage-driven EJB bean, we only have to apply the @Schedule annotation on the method. 
A Timer can be persistent, and so survive server crashes, or transient and be bound to the JVM where it is created.

#####Exemple:
1. basic-schedule
2. static-scheduler



An annotation-based configuration requires recompilation and redeployment of the application upon any change and is suitable only for stable and never-changing schedules.However, watchdogs and timers for maintenance tasks can be realized as static, auto-registered timers.

To register a timer at runtime, you will have to use an instance of the <b>javax.ejb.SchedulerExpression</b> instead of <b>javax.ejb.Scheduler</b> annotation. The timer has to be explicitly registered with the injected <b>javax.ejb.TimerService#createCalendarTimer</b> method. The return value of the createCalendarMethod is an instance of <b>javax.ejb.Timer</b> that represents the configured timer and can be used for cancellation. 
<b>ReferrerScavengerTimer @Singletion</b>

#####Exemple:
1. referrer-scavenger-timer


##ManagedExecutorService
With the Java EE 7 came another solution.
javax.enterprise.concurrent.ManagedScheduledExecutorService provides a managed version of ScheduledExecutorService and can be used to schedule tasks at specified and periodic times.
You can obtain an instance of ManagedScheduledExecutorService with a JNDI lookup using resource environment references. A default <b>ManagedScheduledExecutorService</b> is available under the JNDI name <b>java:comp/DefaultManagedScheduledExecutorService</b>.
You can then obtain ManagedScheduledExecutorService like so:

```

	InitialContext ctx = new InitialContext();
	ManagedScheduledExecutorService executor =
			(ManagedScheduledExecutorService)ctx.lookup("java:comp/DefaultManagedScheduledExecutorService");
```

You can also inject ManagedScheduledExecutorService into the application using

```

	@Resource:
	@Resource(lookup="java:comp/DefaultManagedScheduledExecutorService")
	ManagedScheduledExecutorService executor;
```

## Conventions 
Timer should be created as usual Controls or Boundaries. Although it is not necessary, you could append the "Timer" prostfix to the name of the timer class to indicate the main responsibility.
The Scheduler pattern implementation should not contain any business logic. The TimeOut Method should delegate the calls to control instalces.


