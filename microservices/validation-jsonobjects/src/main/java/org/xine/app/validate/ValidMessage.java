package org.xine.app.validate;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = ValidMessageValidator.class)
@Target({ ElementType.PARAMETER })
@Retention(RUNTIME)
public @interface ValidMessage {

	String expected();

	String message() default "{org.xine.app.validate.ValidMessage}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
