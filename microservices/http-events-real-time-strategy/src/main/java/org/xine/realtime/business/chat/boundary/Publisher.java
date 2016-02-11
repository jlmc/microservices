package org.xine.realtime.business.chat.boundary;

import java.io.IOException;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.xine.realtime.business.chat.control.BrowserWindow;

@WebServlet(name = "Publisher", urlPatterns = { "/publisher" }, asyncSupported = true)
public class Publisher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	Event<BrowserWindow> listeners;

	@Override
	protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
			throws ServletException, IOException {
		req.startAsync();
		final AsyncContext asyncContext = req.getAsyncContext();
		this.listeners.fire(new BrowserWindow(asyncContext));
	}

}
