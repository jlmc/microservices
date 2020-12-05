package org.xine.business.threadlocals.control;

import javax.persistence.EntityManager;

public class ThreadLocalEntityManager {

    private static final ThreadLocal<EntityManager> THREAD_WITH_EM = new ThreadLocal<EntityManager>();

    private ThreadLocalEntityManager() {
    }

    public static void associateWithThread(final EntityManager em) {
        THREAD_WITH_EM.set(em);
    }

    public static EntityManager em() {
        return THREAD_WITH_EM.get();
    }

    public static void cleanupThread() {
        THREAD_WITH_EM.remove();
    }

}
