package org.xine.business.shipments.boundary;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.xine.business.shipments.entity.Load;
import org.xine.business.threadlocals.control.EntityManagerInjector;

@Stateless
@Interceptors(EntityManagerInjector.class)
public class ShipmentService {

    public Load createLoad(final String product, final Integer qty) {
        return new Load.Builder().withItem(product, qty)
                .build();
    }

    public Load find(final Long id) {
        return Load.find(id);
    }
}
