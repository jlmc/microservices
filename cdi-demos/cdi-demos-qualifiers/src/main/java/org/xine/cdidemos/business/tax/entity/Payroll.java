package org.xine.cdidemos.business.tax.entity;

import java.util.Date;
import java.util.List;

public class Payroll {

    private final Date calculateDate;
    private final Double value;
    private final List<Employeer> employeers;

    public Payroll(Date calculateDate, Double value, List<Employeer> employeers) {
        this.calculateDate = calculateDate;
        this.value = value;
        this.employeers = employeers;
    }

    public Date getCalculateDate() {
        return this.calculateDate;
    }

    public List<Employeer> getEmployeers() {
        return this.employeers;
    }

    public Double getValue() {
        return this.value;
    }

}
