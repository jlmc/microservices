package org.xine.business.threadlocals.control;

import static org.xine.business.threadlocals.control.ThreadLocalEntityManager.associateWithThread;
import static org.xine.business.threadlocals.control.ThreadLocalEntityManager.cleanupThread;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class EntityManagerInjector {

    @PersistenceContext
    EntityManager em;

    @AroundInvoke
    public Object associateEntityManagerWithCurrentThread(final InvocationContext ic) throws Exception {
        associateWithThread(this.em);
        try {
            return ic.proceed();
        } finally {
            cleanupThread();
        }
    }
}
