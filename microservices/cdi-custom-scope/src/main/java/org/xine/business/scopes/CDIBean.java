package org.xine.business.scopes;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

public class CDIBean {

	private final Object bean;
	private final Contextual contextual;
	private final CreationalContext creationalContext;

	public CDIBean(Contextual contextual, CreationalContext creationalContext) {
		this.bean = contextual.create(creationalContext);
		this.contextual = contextual;
		this.creationalContext = creationalContext;
	}

	public Object getBean() {
		return this.bean;
	}

	public void destroy() {
		this.contextual.destroy(this.bean, this.creationalContext);
	}

	@Override
	public String toString() {
		return "CDIBean [bean=" + this.bean + "]";
	}
}
