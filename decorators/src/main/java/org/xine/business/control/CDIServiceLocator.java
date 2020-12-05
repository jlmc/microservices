package org.xine.business.control;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CDIServiceLocator {

    // There are three ways to obtain BeanManager:
    // -> @Inject BeanManager bm;
    // -> BeanManager bm = CDI.current().getBeanManager();
    // -> BeanManager bm = getBeanManager()

    private static BeanManager getBeanManager() {
        try {
            final InitialContext context = new InitialContext();
            final BeanManager bm = (BeanManager) context.lookup("java:comp/BeanManager");
            return bm;
        } catch (NamingException | NullPointerException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> T getBean(final Class clazz) {
        final BeanManager beanManager = getBeanManager();
        final Set<Bean<?>> beans = beanManager.getBeans(clazz);
        final Bean<?> bean = beanManager.resolve(beans);
        final CreationalContext<?> context = beanManager.createCreationalContext(bean);
        final Object reference = beanManager.getReference(bean, clazz, context);
        return (T) reference;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> type, Annotation... qualifiers) {
        final BeanManager beanManager = getBeanManager();

        final Set<Bean<?>> beans = beanManager.getBeans(type, qualifiers);
        if (beans == null || beans.isEmpty()) {
            // if (optional) {
            // return null;
            // }
            throw new IllegalStateException(
                    "Could not find beans for Type=" + type + " and qualifiers:" + Arrays.toString(qualifiers));
        }

        final Bean<?> bean = beanManager.resolve(beans);
        final CreationalContext<?> context = beanManager.createCreationalContext(bean);
        final Object reference = beanManager.getReference(bean, type, context);
        return (T) reference;
    }

}
