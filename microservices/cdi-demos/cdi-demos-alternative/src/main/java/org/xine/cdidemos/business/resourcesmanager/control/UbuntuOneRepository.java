package org.xine.cdidemos.business.resourcesmanager.control;

import javax.enterprise.inject.Alternative;

@Alternative
//@Priority(javax.interceptor.Interceptor.Priority.APPLICATION + 1)
public class UbuntuOneRepository implements Repository {

    @Override
    public void put(final String fileName, final byte[] bytes) {
        System.out.println("Ubuntu One repository");
    }

}
