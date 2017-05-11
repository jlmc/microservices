package org.xine.channels.business.heartbeat.boundary;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Qualifier
@Target({ java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Severity {

	Level value() default Level.ESCALATION;

	public static enum Level {
		HEARTBEAT, ESCALATION;

		private Level() {}
	}
}