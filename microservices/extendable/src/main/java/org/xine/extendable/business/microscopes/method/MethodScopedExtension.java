package org.xine.extendable.business.microscopes.method;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;

import org.xine.extendable.business.microscopes.ScopeContext;

public class MethodScopedExtension implements Extension {

    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {
        bbd.addScope(MethodScoped.class, true, false);
        bbd.addInterceptorBinding(MethodScopeEnabled.class);
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
        abd.addContext(new ScopeContext<>(MethodScoped.class));
    }

}
