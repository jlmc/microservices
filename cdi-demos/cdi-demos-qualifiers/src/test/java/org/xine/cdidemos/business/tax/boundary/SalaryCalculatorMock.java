package org.xine.cdidemos.business.tax.boundary;

import javax.enterprise.inject.Vetoed;

import org.xine.cdidemos.business.tax.control.SalaryCalculator;
import org.xine.cdidemos.business.tax.entity.Employeer;

@Vetoed
public class SalaryCalculatorMock extends SalaryCalculator {

    private final double salaryFixed;

    public SalaryCalculatorMock(double salaryFixed) {
        this.salaryFixed = salaryFixed;
    }

    @Override
    public double calculateSalary(Employeer employeer) {
        return this.salaryFixed;
    }

}
