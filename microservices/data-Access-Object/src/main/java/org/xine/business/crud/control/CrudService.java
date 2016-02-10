package org.xine.business.crud.control;

import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class CrudService {

    @PersistenceContext
    EntityManager em;

    public <T> T create(final T t) {
        this.em.persist(t);
        this.em.flush();
        this.em.refresh(t);
        return t;
    }

    public <T> T find(final Class<T> type, final Object id) {
        return this.em.find(type, id);
    }

    public void delete(final Class type, final Object id) {
        final Object ref = this.em.getReference(type, id);
        this.em.remove(ref);
    }

    public <T> T update(final T t) {
        return this.em.merge(t);
    }

    public List findWithNamedQuery(final String namedQueryName) {
        return this.em.createNamedQuery(namedQueryName).getResultList();
    }

    public List findWithNamedQuery(final String namedQueryName, final Map<String, Object> parameters) {
        return findWithNamedQuery(namedQueryName, parameters, 0);
    }

    public List findWithNamedQuery(final String queryName, final int resultLimit) {
        return this.em.createNamedQuery(queryName).setMaxResults(resultLimit).getResultList();
    }

    public <T> List<T> findByNativeQuery(final String sql, final Class<T> type) {
        return this.em.createNativeQuery(sql, type).getResultList();
    }

    public List findWithNamedQuery(final String namedQueryName, final Map<String, Object> parameters, final int resultLimit) {
        final Query query = this.em.createNamedQuery(namedQueryName);

        if (resultLimit > 0) {
            query.setMaxResults(resultLimit);
        }
        parameters.entrySet().forEach(entry -> query.setParameter(entry.getKey(), entry.getValue()));

        return query.getResultList();
    }

}
