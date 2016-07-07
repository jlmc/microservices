package org.xine.business.control;

import java.util.Set;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.enterprise.inject.Decorated;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;

@Decorator
public class BigMessenger implements Messenger {

    @Inject
    @Delegate
    Messenger messenger;

    @SuppressWarnings("cdi-ambiguous-dependency")
    @Inject
    @Decorated
    private Bean<Messenger> bean;

    public String morning() {

        final Class beanClass = this.bean.getBeanClass();
        final Set<InjectionPoint> injectionPoints = this.bean.getInjectionPoints();
        System.out.println(String.format("Bean original: '%s' " + "com injection points: '%s'", beanClass, injectionPoints));


        return "Today is " + this.messenger.morning();
    }

}
