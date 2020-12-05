package org.xine.business.boundary;

import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.xine.business.control.Control;
import org.xine.business.scopes.ManualManagedContext;

@RequestScoped
public class InjectionBoundary {
    
    @Inject 
    Control first;

    @Inject 
    Control second;
    
    @Inject
    Instance<Control> firstInstance;

    @Inject 
    Instance<Control> secondInstance;
    
    public String invokeService(){
        return first.execute();
    }
    
    public boolean sameInstance(){
        return first == second;
    }
    
    public boolean lazyFetch(){
        return firstInstance.get() == secondInstance.get();
    }
    
    public boolean differentLazyInstance(){
        final Control lazyFirst = firstInstance.get();
        ManualManagedContext.getInstance().shutdown();
        final Control lazySecond = secondInstance.get();
        return lazyFirst != lazySecond;
    }
    
    public String injectedAsString(){
        return first.toString() + " | " + second.toString();
    }
    
    @PreDestroy
    public void onDestroy(){
        System.out.println(this.getClass().getName() + " onDestroy");
    }    
}
