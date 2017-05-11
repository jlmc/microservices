package org.xine.xebuy.business;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CrudService {

	@PersistenceContext
	EntityManager em;

	public <T> T create(T t) {
		this.em.persist(t);
		this.em.flush();
		this.create(t);
		return t;
	}

	public <T> T find(Class<T> type, Object id) {
		return this.em.find(type, id);
	}

	public void delete(Class type, Object id) {
		final Object reference = this.em.getReference(type, id);
		this.em.remove(reference);
	}

	public <T> T update(T t) {
		return this.em.merge(t);
	}

	public List findWithNamedQuery(String namedQueryName) {
		return this.em.createNamedQuery(namedQueryName).getResultList();
	}

	public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters) {
		return this.findWithNamedQuery(namedQueryName, parameters, 0);
	}

	public List findWithNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit) {
		final Query query = this.em.createNamedQuery(namedQueryName);
		final Set<java.util.Map.Entry<String, Object>> rawParameters = parameters.entrySet();

		if (resultLimit > 0) {
			query.setMaxResults(resultLimit);
		}

		rawParameters.forEach(e -> query.setParameter(e.getKey(), e.getValue()));

		return query.getResultList();
	}


	public <T> List<T> findByNativeQuery(String sql, Class<T> type) {
		return this.em.createNativeQuery(sql, type).getResultList();
	}
}
