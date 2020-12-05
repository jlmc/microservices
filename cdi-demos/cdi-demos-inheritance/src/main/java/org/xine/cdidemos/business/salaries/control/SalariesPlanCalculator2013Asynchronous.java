package org.xine.cdidemos.business.salaries.control;

import javax.annotation.Priority;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Specializes;

import org.xine.cdidemos.business.salaries.entity.Employeer;

@Alternative
@Priority(javax.interceptor.Interceptor.Priority.APPLICATION)
@Specializes
public class SalariesPlanCalculator2013Asynchronous extends SalariesPlanCalculator2013 {

    @Override
    public double earningsEstimates(final Employeer employee) {
        System.out.println("SalariesPlanCalculator2013Asynchronous");
        return 10;
    }

}
