package org.xine.xamazon.bulk.control;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.enterprise.inject.Produces;
import java.util.concurrent.ThreadFactory;

/**
 * Uses the Java EE 7 {@link javax.enterprise.concurrent.ManagedThreadFactory} for thread creations. This
 * class can be overridden {@link javax.enterprise.inject.Specializes} in Java EE 6 or integration tests
 * to create unmanaged threads.
 */
public class ThreadFactoryExposer {

    @Resource
    ManagedThreadFactory threadFactory;

    @Produces
    public ThreadFactory expose() {
        return this.threadFactory;
    }
}
