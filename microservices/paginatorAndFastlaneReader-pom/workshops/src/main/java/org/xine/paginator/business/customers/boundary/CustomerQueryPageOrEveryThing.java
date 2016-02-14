package org.xine.paginator.business.customers.boundary;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.xine.paginator.business.customers.entity.Customer;

@Stateless
public class CustomerQueryPageOrEveryThing {

	@PersistenceContext
	EntityManager em;

	private static final int MAX_PAGE_SIZE = 100;

	public List<Customer> getAllCustomers(int maxResults) {
		if (maxResults < 0 || maxResults > MAX_PAGE_SIZE) {
			maxResults = MAX_PAGE_SIZE;
		} else {
			maxResults += 1;
		}

		final TypedQuery<Customer> createNamedQuery = this.em.createNamedQuery("Customer.findAll", Customer.class);
		createNamedQuery.setMaxResults(maxResults);
		return createNamedQuery.getResultList();
	}

}
