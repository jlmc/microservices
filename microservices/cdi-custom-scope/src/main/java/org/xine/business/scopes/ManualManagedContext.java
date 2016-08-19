package org.xine.business.scopes;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

public class ManualManagedContext implements Context {

	private static final Logger LOGGER = Logger.getLogger(ManualManagedContext.class.getName());
	private final static ManualManagedContext INSTANCE = new ManualManagedContext();

	private final ConcurrentHashMap<String, CDIBean> context;
	private boolean active;

	private ManualManagedContext() {
		this.context = new ConcurrentHashMap<String, CDIBean>();
		this.active = true;
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
		System.out.println(
				"Context: " + this.context + " Contextual " + contextual + " CreationalContext: " + creationalContext);

		final Bean bean = (Bean) contextual;
		final String beanName = bean.getBeanClass().getName();
		final T foundBean = get(contextual);

		if (foundBean != null) {
			return foundBean;
		}
		
		final CDIBean cdiBean = new CDIBean(contextual, creationalContext);
		this.context.put(beanName, cdiBean);
		return (T) cdiBean.getBean();
	}

	public Map<String, CDIBean> getBeanContext() {
		return this.context;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public <T> T get(Contextual<T> contextual) {
		LOGGER.info("---Contextual: " + contextual);

		final String beanName = ((Bean) contextual).getBeanClass().getName();
		final CDIBean cdiBean = this.context.get(beanName);

		if (cdiBean == null) {
			return null;
		}

		return (T) cdiBean.getBean();
	}

	@Override
	public boolean isActive() {
		return this.active;
	}

	public void shutdown() {
		for (final CDIBean contextual : this.context.values()) {
			contextual.destroy();
		}

		this.context.clear();
	}

}
