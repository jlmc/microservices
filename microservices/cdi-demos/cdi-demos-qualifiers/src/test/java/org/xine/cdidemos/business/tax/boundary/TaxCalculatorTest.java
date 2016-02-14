package org.xine.cdidemos.business.tax.boundary;

import org.junit.Assert;
import org.junit.Test;
import org.xine.cdidemos.business.tax.control.SalaryCalculator;
import org.xine.cdidemos.business.tax.entity.Employeer;

public class TaxCalculatorTest {


	@Test
	public void testCalculateIncomeTax() {
		final Employeer employeer = new Employeer.Builder().build();

		final SalaryCalculator calculadoraSalarios = new SalaryCalculatorMock(3000.0);

		final TaxCalculator taxCalculator = new TaxCalculator();
		taxCalculator.salaryCalculator = calculadoraSalarios;

		final double tax = taxCalculator.calculateIncomeTax(employeer);

		Assert.assertEquals(129.4, tax, 0.0001);
	}

}
