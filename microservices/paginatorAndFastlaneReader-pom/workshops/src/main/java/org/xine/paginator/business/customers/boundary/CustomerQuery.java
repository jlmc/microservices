package org.xine.paginator.business.customers.boundary;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.xine.paginator.business.customers.entity.Customer;

@Stateful
public class CustomerQuery implements Iterator<List<Customer>>{

    @PersistenceContext
    EntityManager em;

    private int index = 0;
    private int pageSize = 10;
    private boolean next = true;

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public List<Customer> next() {
        List<Customer> retVal = null;

        // final Query query = this.em.createQuery("SELECT c FROM Customer c");
        // query.setFirstResult(getFirst());
        // query.setMaxResults(this.pageSize);
        //
        // retVal = query.getResultList();

        final CriteriaBuilder builder = this.em.getCriteriaBuilder();
        final CriteriaQuery<Customer> criteria = builder.createQuery(Customer.class);
        final Root<Customer> from = criteria.from(Customer.class);
        criteria.select(from);
        criteria.orderBy(builder.asc(from.get("id")));

        final TypedQuery<Customer> createQuery = this.em.createQuery(criteria);
        createQuery.setFirstResult(getFirst());
        createQuery.setMaxResults(this.pageSize);


        retVal = createQuery.getResultList();

        if (retVal.isEmpty()) {
            this.next = false;
            retVal = Collections.emptyList();
        }
        this.index++;
        return retVal;
    }

    private int getFirst() {
        return this.index * this.pageSize;
    }

    @Override
    public boolean hasNext() {
        return this.next;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Operation remove it is not supported...");
    }

    @Remove
    public void close() {
        this.em.clear();
        this.em.close();
    }

}
