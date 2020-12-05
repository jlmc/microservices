package org.xine.cdidemos.presentation;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xine.cdidemos.business.tax.boundary.TaxCalculator;
import org.xine.cdidemos.business.tax.entity.Employeer;

@WebServlet("/hello-cdi")
public class HelloCDI extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    private Logger logger;

    @Inject
    private TaxCalculator taxCalculator;

    public HelloCDI() {
        // this.logger.log(Level.INFO, "Constror");
    }

    public void ok() {
        this.logger.info("is ready....");

    }

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse resp)
                    throws ServletException, IOException {

        final double baseSalary = Double.parseDouble(req.getParameter("salary"));

        final Employeer employeer = new Employeer.Builder().withBaseSalary(baseSalary).build();

        this.logger.info("performing calculations...");

        final double tax = this.taxCalculator.calculateIncomeTax(employeer);

        resp.getOutputStream().print(String.format("Base Salaray: %.2f\nTax: %.2f", baseSalary, tax));

        this.logger.info("END...");
    }


    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse resp)
                    throws ServletException, IOException {
        this.doGet(req, resp);
    }

}
