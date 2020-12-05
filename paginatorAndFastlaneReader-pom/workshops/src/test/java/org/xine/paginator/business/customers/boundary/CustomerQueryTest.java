package org.xine.paginator.business.customers.boundary;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xine.paginator.business.customers.entity.Customer;

public class CustomerQueryTest {

    private EntityManager em;
    private EntityTransaction et;

    private CustomerQuery query;

    @Before
    public void initialize() {
        this.em = Persistence.createEntityManagerFactory("integration-test").createEntityManager();
        this.et = this.em.getTransaction();

        this.query = new CustomerQuery();
        this.query.em = this.em;

        // fill the customers repository
        this.et.begin();
        for (int i = 0; i < 50; i++) {
            this.em.persist(new Customer("Duke " + i));
        }
        this.et.commit();
    }

    @Test
    public void test() {
        while (this.query.hasNext()) {
            final List<Customer> customers = this.query.next();
            System.out.println("Size: " + customers.size());

            customers.forEach(System.out::println);
        }
    }

    @After
    public void cleanUp() {
        this.et.begin();
        this.em.createQuery("delete from Customer").executeUpdate();
        this.et.commit();

    }


}
