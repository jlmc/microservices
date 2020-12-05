package org.xine.paginator.business.customers.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xine.paginator.business.customers.entity.Customer;

public class CustomerQueryPageOrEveryThingTest {

    private EntityManager em;
    private EntityTransaction et;

    private CustomerQueryPageOrEveryThing query;

    @Before
    public void initialize() throws Exception {
        this.em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
        this.et = this.em.getTransaction();

        this.query = new CustomerQueryPageOrEveryThing();
        this.query.em = this.em;

        // fill the customers repository
        this.et.begin();
        for (int i = 0; i < 50; i++) {
            this.em.persist(new Customer("Duke " + i));
        }
        this.et.commit();
    }

    @After
    public void cleanUp() {
        this.et.begin();
        this.em.createQuery("delete from Customer").executeUpdate();
        this.et.commit();

    }

    @Test
    public void testGetAllCustomers() {
        final int pageSize = 5;
        final List<Customer> all = this.query.getAllCustomers(pageSize);

        assertNotNull(all);
        assertEquals(pageSize + 1, all.size());

        all.forEach(System.out::println);

    }

}
