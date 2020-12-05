package org.xine.cdidemos.presentation.qualifiers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xine.cdidemos.business.qualifiers.control.PayrollCalculator;
import org.xine.cdidemos.business.qualifiers.control.Simulator;
import org.xine.cdidemos.business.tax.entity.Employeer;

@WebServlet("/simulator")
public class PayrollCalculatorSimulator extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    @Simulator
    private PayrollCalculator payrollCalculator;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final Employeer employeer1 = new Employeer.Builder().withBaseSalary(1000.0).build();
        final Employeer employeer2 = new Employeer.Builder().withBaseSalary(2000.0).build();
        final Employeer employeer3 = new Employeer.Builder().withBaseSalary(3000.0).build();

        final List<Employeer> employeers = Arrays.asList(employeer1, employeer2, employeer3);

        this.payrollCalculator.calcPayroll(employeers);

        resp.getOutputStream().print("All do with success");
    }

}
