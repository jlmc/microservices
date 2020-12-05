package org.xine.cdidemos.business.qualifiers.control;

import java.util.Collection;

import org.xine.cdidemos.business.tax.entity.Employeer;
import org.xine.cdidemos.business.tax.entity.Payroll;

@Simulator(plantype = PlanType.VERSION_2015)
public class Simulator2015 implements PayrollCalculator {

    @Override
    public Payroll calcPayroll(Collection<Employeer> employeers) {
        System.out.println("Simulator2015 ...");
        return null;
    }

}
