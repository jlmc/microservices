package org.xine.xamazon.bulk.boundary;

import org.xine.xamazon.bulk.control.ExecutorConfiguration;

/**
 * @author costa
 *         on 23/05/2017.
 */
public class ExecutorConfigurator {

    /**
     *
     * @param pipelineName the name used within the {@link Dedicated} qualifier.
     * @return a default configuration, unless overridden
     */
    public ExecutorConfiguration forPipeline(String pipelineName) {
        return defaultConfigurator();
    }

    /**
     *
     * @return the default configuration for all injected
     * {@link java.util.concurrent.ExecutorService} instances (unless overridden and specialized
     * {@link javax.enterprise.inject.Specializes})
     */
    public ExecutorConfiguration defaultConfigurator() {
        return ExecutorConfiguration.defaultConfiguration();
    }
}
