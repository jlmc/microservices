package org.xine.business.scopes;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

public class ManualManagedContext implements Context {

    private ConcurrentHashMap<String, CDIBean> context = null;
    private final static ManualManagedContext INSTANCE = new ManualManagedContext();
    private boolean active;

    private ManualManagedContext() {
        context = new ConcurrentHashMap<String, CDIBean>();
        active = true;
    }

    public static ManualManagedContext getInstance() {
        return INSTANCE;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return ManualScoped.class;
    }

    @Override
    public <T> T get(Contextual<T> contextual, CreationalContext<T> creationalContext) {
        System.out.println("Context: " + context + " Contextual " + contextual + " CreationalContext: " + creationalContext);
        final Bean bean = (Bean) contextual;
        final String beanName = bean.getBeanClass().getName();
        final T foundBean = get(contextual);
        if (foundBean != null) {
            return foundBean;
        } else {
            final CDIBean cdiBean = new CDIBean(contextual, creationalContext);
            context.put(beanName, cdiBean);
            return (T)cdiBean.getBean();
        }
    }

    @Override
    public <T> T get(Contextual<T> contextual) {
        System.out.println("---Contextual: " + contextual);
        final String beanName = ((Bean)contextual).getBeanClass().getName();
        final CDIBean cdiBean = context.get(beanName);
        if(cdiBean == null) {
			return null;
		}
        return (T) cdiBean.getBean();
    }

    @Override
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public Map<String, CDIBean> getBeanContext() {
        return context;
    }
    
    public void shutdown(){
        for (final CDIBean contextual : context.values()) {
            contextual.destroy();
        }
        context.clear();
    }
}