package org.xine.business.control;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.xine.business.scopes.ManualScoped;

@ManualScoped
public class AnotherControl {
    public final static AtomicInteger INSTANCE_COUNTER = new AtomicInteger(0);
    
    @PostConstruct
    public void onCreate(){
        INSTANCE_COUNTER.incrementAndGet();
    }

    public String execute(){
        return "+";
    }
    
    @PreDestroy
    public void onDestroy(){
        System.out.println(this.getClass().getName() + " onDestroy");
        INSTANCE_COUNTER.decrementAndGet();
    }
}
