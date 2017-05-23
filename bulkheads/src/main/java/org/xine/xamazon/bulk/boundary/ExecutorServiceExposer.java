package org.xine.xamazon.bulk.boundary;

import org.xine.xamazon.bulk.control.ExecutorConfiguration;
import org.xine.xamazon.bulk.control.PipelineStore;
import org.xine.xamazon.bulk.entity.Pipeline;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.Annotated;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Exposes a {@link Dedicated} {@link ExecutorService}.
 */
public class ExecutorServiceExposer {

    @Inject
    ThreadFactory threadFactory;

    @Inject
    PipelineStore pipelineStore;

    @Inject
    ExecutorConfigurator executorConfigurator;

    @Produces
    @Dedicated
    public ExecutorService exposeExecutorService(InjectionPoint ip) {
        String pipelineName = this.getPipelineName(ip);

        final Optional<Pipeline> pipeline = Optional.ofNullable(this.pipelineStore.get(pipelineName));
        if (pipeline.isPresent()) {
            return pipeline.map(Pipeline::getExecutor).get();
        }

        final ExecutorConfiguration executorConfiguration = this.executorConfigurator.forPipeline(pipelineName);

        /*
        RejectedExecutionHandler rejectedExecutionHandler = executorConfiguration.getRejectedExecutionHandler();
        if (rejectedExecutionHandler == null) {
            rejectedExecutionHandler = this::onRejectedExecution;
        }
        */

        ExecutorService executorService = this.createThreadPoolExecutor(executorConfiguration);
        this.pipelineStore.add(Pipeline.of(pipelineName, executorService));
        return executorService;
    }

    private ExecutorService createThreadPoolExecutor(ExecutorConfiguration executorConfiguration) {

        final int queueCapacity = executorConfiguration.getQueueCapacity();
        BlockingQueue<Runnable> queue;

        if (queueCapacity > 0) {
            queue = new ArrayBlockingQueue<Runnable>(queueCapacity);
        } else {
            queue = new SynchronousQueue<>();
        }

        return new ThreadPoolExecutor(
                executorConfiguration.getCorePoolSize(),
                executorConfiguration.getMaxPoolSize(),
                executorConfiguration.getKeepAliveTime(),
                executorConfiguration.getKeepAliveTimeUnit(),
                queue,
                threadFactory,
                executorConfiguration.getRejectedExecutionHandler());
    }

    private String getPipelineName(InjectionPoint ip) {
        Annotated annotated = ip.getAnnotated();
        Dedicated dedicated = annotated.getAnnotation(Dedicated.class);
        String name;
        if (dedicated != null && !Dedicated.DEFAULT.equals(dedicated.value())) {
            name = dedicated.value();
        } else {
            name = ip.getMember().getName();
        }
        return name;
    }
}
