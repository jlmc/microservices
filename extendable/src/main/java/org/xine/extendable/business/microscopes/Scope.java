package org.xine.extendable.business.microscopes;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

public class Scope<Key> {
	private final Instance<?> NOTHING = new Instance<>(null, null, null);

	private final Map<Contextual<?>, Instance> instances = new ConcurrentHashMap<>();

	private final Key key;

	public Scope(final Key key) {
		this.key = key;
	}

	public Key getKey() {
		return key;
	}

	/**
	 * Returns an instance of the Bean (Contextual), creating it if necessary
	 * 
	 * @param contextual
	 *            the Bean type to create
	 * @param <T>
	 *            the Java type of the bean instance itself
	 * @return existing or newly created bean instance, never null
	 */
	public <T> T get(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {
		return (T) instances.computeIfAbsent(contextual, c -> new Instance<>(contextual, creationalContext)).get();
	}

	/**
	 * Returns the existing instance of the Bean or null if none exists yet
	 * 
	 * @param contextual
	 *            the Bean type to create
	 * @param <T>
	 *            the Java type of the bean instance itself
	 * @return existing the bean instance or null
	 */
	public <T> T get(final Contextual<T> contextual) {
		return (T) instances.getOrDefault(contextual, NOTHING).get();
	}

	/**
	 * Destroy all the instances in this scope
	 */
	public void destroy() {
		// TODO We really should ensure no more instances can be added during or
		// after this
		instances.values().stream().forEach(Instance::destroy);
		instances.clear();
	}

	private class Instance<T> {
		private final T instance;
		private final CreationalContext<T> creationalContext;
		private final Contextual<T> contextual;

		public Instance(final Contextual<T> contextual, final CreationalContext<T> creationalContext) {

			this(contextual, creationalContext, contextual.create(creationalContext));
		}

		public Instance(Contextual<T> contextual, CreationalContext<T> creationalContext, T instance) {
			this.instance = instance;
			this.creationalContext = creationalContext;
			this.contextual = contextual;
		}

		public T get() {
			return instance;
		}

		public void destroy() {
			contextual.destroy(instance, creationalContext);
		}
	}

}
