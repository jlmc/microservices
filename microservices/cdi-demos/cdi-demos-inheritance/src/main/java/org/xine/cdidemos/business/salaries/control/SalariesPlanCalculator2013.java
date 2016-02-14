package org.xine.cdidemos.business.salaries.control;

import org.xine.cdidemos.business.salaries.entity.Employeer;

@TaxCal
public class SalariesPlanCalculator2013 implements SalariesPlanCalculator {

	@Override
	public double earningsEstimates(final Employeer employee) {
		System.out.println("SalariesPlanCalculator2013");
		return 5;
	}

}
