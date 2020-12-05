package org.xine.business.calculation;

import javax.ejb.Stateless;

import org.xine.business.audit.boundary.Auditable;
import org.xine.business.security.boundary.Secure;

@Stateless
public class Calculator {

    @Auditable
    @Secure
    public Double calc() {
        System.out.println("Hello friends from calculator");
        return Double.valueOf(5.0D);
    }

}
