package org.xine.channels.beanlocator;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CdiUtil {
    private Context context;

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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> T lookup(final Class<T> clazz) throws BeanLocationException {
        final BeanManager beanManager = getBeanManager();

        final Bean<T> handlerBean = (Bean) beanManager.getBeans(clazz, new Annotation[0]).iterator().next();
        final CreationalContext<T> ctx = beanManager.createCreationalContext(handlerBean);
        final T handler = (T) beanManager.getReference(handlerBean, clazz, ctx);
        return handler;
    }

    public BeanManager getBeanManager() {
        /*
         * @Inject BeanManager bm; BeanManager bm =
         * CDI.current().getBeanManager();
         */

        try {
            final InitialContext context = new InitialContext();
            final BeanManager bm = (BeanManager) context.lookup("java:comp/BeanManager");
            return bm;
        } catch (NamingException | NullPointerException ex) {
            System.out.println(ex);
            throw new RuntimeException(ex);
        }
    }

    @SuppressWarnings({ "unchecked", "unused" })
    private <T> T jndiLookup(final String jndiName, final Class<T> clazz) throws BeanLocationException {
        T returnObject = null;
        try {
            final Context ctx = retrieveContext();
            returnObject = (T) ctx.lookup(jndiName);
        } catch (final NamingException ne) {
            throw new BeanLocationException(
                    "Could not retrieve Object because of a naming exception: " + ne.getMessage(), ne);
        } catch (final ClassCastException cce) {
            throw new BeanLocationException("Retrieved an Object, but it could not be cast to the requested type.",
                    cce);
        }
        return returnObject;
    }

    private Context retrieveContext() throws BeanLocationException {
        if (this.context == null) {
            try {
                this.context = new InitialContext();
            } catch (final NamingException ex) {
                throw new BeanLocationException("Could not retrieve intial context.", ex);
            }
        }
        return this.context;
    }
}