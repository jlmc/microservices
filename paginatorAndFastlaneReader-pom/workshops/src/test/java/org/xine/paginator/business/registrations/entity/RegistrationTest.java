package org.xine.paginator.business.registrations.entity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.function.BiFunction;

import org.junit.Test;

public class RegistrationTest {

    Registration cut;

    @Test
    public void getNetPrice() {
        final int expected = 600;
        this.cut = new Registration(false, 2, 1);
        final int actualNetPrice = this.cut.getNetPrice();
        assertThat(expected, is(actualNetPrice));
    }

    @Test
    public void getTotalPrice() {
        final boolean vatAvailable = true;
        this.cut = new Registration(vatAvailable, 2, 1);

        final BiFunction<Boolean, Integer, Integer> taxCalculator = mock(BiFunction.class);
        this.cut.setCalculator(taxCalculator);

        final int netPrice = this.cut.getNetPrice();
        final int expectedPrice = 42;
        when(taxCalculator.apply(vatAvailable, netPrice)).thenReturn(expectedPrice);

        final int totalPrice = this.cut.getTotalPrice();
        assertThat(totalPrice, is(expectedPrice));
        verify(taxCalculator).apply(vatAvailable, netPrice);

    }

}
