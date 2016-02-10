package org.xine.business.crud.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DAO<K, T> {

    public T create(T t);

    public T find(K id);

    public void delete(T t);

    public T update(T t);

    public Collection<T> findByNamedQuery(String queryName);

    public Collection<T> findByNamedQuery(String queryName, int resultLimit);

    public List<T> findByNamedQuery(String namedQueryName, Map<String, Object> parameters);

    public List<T> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
