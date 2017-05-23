package org.xine.xamazon.bulk.boundary;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author costa
 */
@Qualifier
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Dedicated {
    public final static String DEFAULT = "-";

    /**
     * Defines the name for the pipeline (Executor with statistics). If the name
     * is unset, the name of the field is going to be used instead.
     *
     * @return the name of the pipeline. Either a new pipeline is going to be
     * created, or an existing returned.
     */
    @Nonbinding
    String value() default DEFAULT;
}
