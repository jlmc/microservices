package org.xine.xamazon.bulk.control;

import org.xine.xamazon.bulk.entity.Pipeline;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author costa
 *         on 22/05/2017.
 */
@ApplicationScoped
public class PipelineStore {

    ConcurrentHashMap<String, Pipeline> pipelines;

    @PostConstruct
    void init() {
        this.pipelines = new ConcurrentHashMap<>();
    }

    @PreDestroy
    public void shutdown() {
        this.pipelines.values().stream().forEach(Pipeline::shutdown);
        this.pipelines.clear();
    }

    public Pipeline get(String pipelineName) {
        return this.pipelines.get(pipelineName);
    }

    public void add(Pipeline pipeline) {
        this.pipelines.put(pipeline.getPipelineName(), pipeline);
    }
}
