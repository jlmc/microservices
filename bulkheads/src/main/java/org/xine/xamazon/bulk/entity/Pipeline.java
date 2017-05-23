package org.xine.xamazon.bulk.entity;

import java.util.concurrent.ExecutorService;

/**
 * @author costa
 */
public class Pipeline {

    private final String pipelineName;
    private final ExecutorService executor;

    private Pipeline(String pipelineName, ExecutorService executor) {
        this.pipelineName = pipelineName;
        this.executor = executor;
    }

    public static Pipeline of (String pipelineName, ExecutorService executor) {
        return new Pipeline(pipelineName, executor);
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void shutdown() {
        this.executor.shutdown();
    }

    public String getPipelineName() {
        return pipelineName;
    }
}
