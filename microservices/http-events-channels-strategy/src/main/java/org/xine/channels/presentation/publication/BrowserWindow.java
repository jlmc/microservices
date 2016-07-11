package org.xine.channels.presentation.publication;

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class BrowserWindow {

	private final AsyncContext asyncContext;
	private final ServletResponse response;
	private String channel;

	public BrowserWindow(final AsyncContext asyncContext) {
		this.asyncContext = asyncContext;
		this.response = this.asyncContext.getResponse();
		this.response.setContentType("application/xml");
	}

	public BrowserWindow(final AsyncContext asyncContext, final String channel) {
		this(asyncContext);
		this.channel = channel;
	}

	public Writer getWriter() {
		try {
			return this.asyncContext.getResponse().getWriter();
		} catch (final IOException e) {
			throw new IllegalStateException("Cannot return writer: " + e, e);
		}
	}

	public void nothingToSay() {
		final HttpServletResponse httpServletResponse = (HttpServletResponse) this.response;
		httpServletResponse.setStatus(204);
		this.asyncContext.complete();
	}

	public String getChannel() {
		return this.channel;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BrowserWindow other = (BrowserWindow) obj;
		if ((this.asyncContext != other.asyncContext)
				&& ((this.asyncContext == null) || (!this.asyncContext.equals(other.asyncContext)))) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.asyncContext);
	}

	@Override
	public String toString() {
		return "BrowserWindow{asyncContext=" + this.asyncContext + ", response=" + this.response + ", channel="
				+ this.channel + '}';
	}

	public void send() {
		// Auto-generated method stub

	}

}
