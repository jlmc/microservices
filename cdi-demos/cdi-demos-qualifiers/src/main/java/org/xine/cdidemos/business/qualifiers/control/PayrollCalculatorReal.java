package org.xine.cdidemos.business.qualifiers.control;

import java.util.Collection;

import org.xine.cdidemos.business.tax.entity.Employeer;
import org.xine.cdidemos.business.tax.entity.Payroll;

public class PayrollCalculatorReal implements PayrollCalculator {

    @Override
    public Payroll calcPayroll(Collection<Employeer> employeers) {
        System.out.println("Real PayrollCalculatorReal");
        return null;
    }

}
