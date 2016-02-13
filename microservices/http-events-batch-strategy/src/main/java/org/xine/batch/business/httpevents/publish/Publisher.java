package org.xine.batch.business.httpevents.publish;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Publisher", urlPatterns = { "/Publisher" }, asyncSupported = true)
public class Publisher extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	Event<BrowserWindow> listeners;

	@Override
	public void doGet(final HttpServletRequest request, final HttpServletResponse response) {
		final AsyncContext async = request.startAsync();
		this.listeners.fire(new BrowserWindow(async));
	}
}
