package org.xine.channels.beanlocator;

import java.lang.annotation.Annotation;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class CdiUtil {
	private Context context;

	public <T> T lookup(final Class<T> clazz) throws BeanLocationException {
		final BeanManager beanManager = jndiLookup("java:comp/BeanManager", BeanManager.class);

		final Bean<T> handlerBean = (Bean) beanManager.getBeans(clazz, new Annotation[0]).iterator().next();
		final CreationalContext<T> ctx = beanManager.createCreationalContext(handlerBean);
		final T handler = (T) beanManager.getReference(handlerBean, clazz, ctx);
		return handler;
	}

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