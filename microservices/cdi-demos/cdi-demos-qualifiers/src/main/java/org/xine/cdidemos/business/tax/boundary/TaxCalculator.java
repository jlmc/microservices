package org.xine.cdidemos.business.tax.boundary;

import javax.inject.Inject;

import org.xine.cdidemos.business.tax.control.SalaryCalculator;
import org.xine.cdidemos.business.tax.entity.Employeer;

public class TaxCalculator {

    @Inject
    SalaryCalculator salaryCalculator;

    public TaxCalculator() {
        System.out.println("Const " + getClass().getSimpleName());
    }

    public double calculateIncomeTax(Employeer employeer) {
        final double salario = this.salaryCalculator.calculateSalary(employeer);

        double aliquota = 0.0;
        double parcelaDeduzir = 0.0;

        if (salario <= 1710.78) {
            aliquota = 0.0;
            parcelaDeduzir = 0.0;
        } else if (salario > 1710.78 && salario <= 2563.91) {
            aliquota = 7.5 / 100;
            parcelaDeduzir = 128.31;
        } else if (salario > 2563.91 && salario <= 3418.59) {
            aliquota = 15.0 / 100;
            parcelaDeduzir = 320.60;
        } else if (salario > 3418.59 && salario <= 4271.59) {
            aliquota = 22.5 / 100;
            parcelaDeduzir = 577.0;
        } else if (salario > 4271.59) {
            aliquota = 27.5 / 100;
            parcelaDeduzir = 790.58;
        }

        final double impostoSemDesconto = salario * aliquota;

        return impostoSemDesconto - parcelaDeduzir;
    }

}
