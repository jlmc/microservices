package org.xine.channels.presentation.publication.escalation;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xine.channels.presentation.publication.BrowserWindow;


@WebServlet(name = "CometEscalator", urlPatterns = { "/CometEscalator/*" }, asyncSupported = true)
public class CometEscalator extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Inject
    Event<BrowserWindow> events;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse resp)
            throws ServletException, IOException {

        final AsyncContext startAsync = request.startAsync();
        final String channel = extractChannel(request.getRequestURI());
        this.events.fire(new BrowserWindow(startAsync, channel));
    }

    private String extractChannel(final String requestURI) {
        final int lastIndexOf = requestURI.lastIndexOf("/");
        final String channel = requestURI.substring(lastIndexOf + 1);
        return channel;
    }
}
