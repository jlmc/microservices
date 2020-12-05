package org.xine.cdidemos.business.qualifiers.control;

import java.util.Collection;

import org.xine.cdidemos.business.tax.entity.Employeer;
import org.xine.cdidemos.business.tax.entity.Payroll;

public interface PayrollCalculator {

    Payroll calcPayroll(Collection<Employeer> employeers);

}
