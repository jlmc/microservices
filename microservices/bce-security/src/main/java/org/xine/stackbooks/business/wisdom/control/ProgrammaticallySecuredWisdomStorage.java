package org.xine.stackbooks.business.wisdom.control;

import java.security.Principal;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.SessionContext;
import javax.ejb.Singleton;
import javax.inject.Inject;

@DeclareRoles("ADMIN")
@Singleton
public class ProgrammaticallySecuredWisdomStorage {

    @Resource
    SessionContext sc;

    @Inject
    Principal principal;

    private String wisdom;

    @PostConstruct
    public void initialize() {
        this.wisdom = "Java Programming Language Rocks!!!";
    }

    public void wisdom(String wisdom) {
        if (!this.sc.isCallerInRole("ADMIN")) {
            throw new IllegalStateException("Only ADMIN can change the wisdom! Are you a blogger?");
        }
        this.wisdom = wisdom;
    }

    public String wisdom() {
        return this.wisdom;
    }
}
