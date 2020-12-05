package org.xine.stackbooks.business.wisdom.control;


import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;

import org.xine.stackbooks.business.security.boundary.AllowedTo;
import org.xine.stackbooks.business.security.boundary.Guard;
import org.xine.stackbooks.business.security.entity.Permission;

@Singleton
@Interceptors(Guard.class)
public class CustomSecuredWisdomStorage {

    private String wisdom;

    @PostConstruct
    public void initialize() {
        this.wisdom = "Java Programming Language Rocks!!!";
    }

    @AllowedTo(Permission.WRITE)
    public void wisdom(String wisdom) {
        this.wisdom = wisdom;
    }

    @AllowedTo(Permission.READ)
    public String wisdom() {
        return this.wisdom;
    }

}
