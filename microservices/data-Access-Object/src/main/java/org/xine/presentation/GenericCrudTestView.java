package org.xine.presentation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xine.business.books.boundary.BookManager;

@WebServlet("/book")
public class GenericCrudTestView extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private BookManager crudTest;

    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
            throws  IOException {

        final String isbn = request.getParameter("isbn");
        final String name = request.getParameter("name");

        response.setContentType("text/html;charset=UTF-8");
        final PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>GenericCrudTest Result</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Result: " + this.crudTest.createBook(isbn, name) + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
