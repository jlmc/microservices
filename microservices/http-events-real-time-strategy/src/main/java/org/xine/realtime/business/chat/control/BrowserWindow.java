package org.xine.realtime.business.chat.control;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class BrowserWindow {

    public static final String CONTENT_TYPE = "text/plain";
    public static final long NO_TIMEOUT = -1;

    private final AsyncContext asyncContext;
    private final ServletResponse response;

    public BrowserWindow(final AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
        this.response = this.asyncContext.getResponse();
        this.response.setContentType(CONTENT_TYPE);
        this.asyncContext.setTimeout(NO_TIMEOUT);
    }

    public void sendAndCommit(final String message) {
        send(message);
        commit();
    }

    private void commit() {
        try {
            this.asyncContext.complete();
        } catch (final Exception e) {
            // do nothing
        }
    }

    public void send(final String message) {
        getWriter().println(message);
    }


    private PrintWriter getWriter() {
        try {
            return this.asyncContext.getResponse().getWriter();
        } catch (final IOException e) {
            throw new IllegalStateException("can't return the writer: " + e, e);
        }
    }

    public void nothingToSay() {
        final HttpServletResponse httpServletResponse = (HttpServletResponse) this.response;
        httpServletResponse.setStatus(204);
        this.asyncContext.complete();
    }
}
