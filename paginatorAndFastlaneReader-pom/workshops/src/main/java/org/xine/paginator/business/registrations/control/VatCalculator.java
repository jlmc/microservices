package org.xine.paginator.business.registrations.control;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;

public class VatCalculator {

    public int calculateTotal(final boolean vatIdAvailable, final int price) {
        final BigDecimal net = valueOf(price);
        if (vatIdAvailable) {
            return net.intValue();
        } else {
            return net.add(net.multiply(valueOf(0.19))).intValue();
        }
    }
}
