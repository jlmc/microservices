package org.xine.business.crud.control;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface DAO<K, T> {

    T create(T t);

    T find(K id);

    void delete(T t);

    T update(T t);

    Collection<T> findByNamedQuery(String queryName);

    Collection<T> findByNamedQuery(String queryName, int resultLimit);

    List<T> findByNamedQuery(String namedQueryName, Map<String, Object> parameters);

    List<T> findByNamedQuery(String namedQueryName, Map<String, Object> parameters, int resultLimit);

}
