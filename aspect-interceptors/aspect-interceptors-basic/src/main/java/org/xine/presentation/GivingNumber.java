package org.xine.presentation;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xine.business.calculation.Calculator;

@WebServlet(name = "GivingNumber", urlPatterns = { "/GivingNumber/*" })
public class GivingNumber extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    Calculator calc;

    @Override
    protected void doGet(
            final HttpServletRequest request,
            final HttpServletResponse response)
                    throws ServletException, IOException {

        request.getParameter("number");
        final Double value = this.calc.calc();
        response.getWriter().println(value);
    }

    @Override
    protected void doPost(
            final HttpServletRequest request,
            final HttpServletResponse response)
                    throws ServletException, IOException {
        doGet(request, response);
    }

}
