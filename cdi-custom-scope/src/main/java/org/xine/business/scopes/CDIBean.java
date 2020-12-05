package org.xine.business.scopes;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

public class CDIBean {
    private final Object bean;
    private final Contextual contextual;
    private final CreationalContext creationalContext;

    public CDIBean(Contextual contextual, CreationalContext creationalContext) {
        bean = contextual.create(creationalContext);
        this.contextual = contextual;
        this.creationalContext = creationalContext;
    }

    public Object getBean() {
        return bean;
    }

    public void destroy() {
        contextual.destroy(bean, creationalContext);
    }

    @Override
    public String toString() {
        return "CDIBean{" + "bean=" + bean + '}';
    }
}

