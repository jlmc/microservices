package org.xine.stackbooks.business.security.boundary;

import java.io.IOException;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class Authenticator implements Filter {

    @Inject
    Logger logger;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {}

    /*
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse resp = (HttpServletResponse) response;
        if (req.authenticate(resp)) {
            chain.doFilter(req, resp);
        }
    }
    */

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        this.logger.info("request....");

        // for now just change of responsability
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
