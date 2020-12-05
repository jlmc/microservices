package org.xine.xebuy.business.plugin.serializer;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

@Target({ TYPE, FIELD, METHOD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface Serialization {

    public enum PlanType {
        DEFAULT, OPTIMIZED;
    }


    PlanType plantype() default PlanType.DEFAULT;


}
