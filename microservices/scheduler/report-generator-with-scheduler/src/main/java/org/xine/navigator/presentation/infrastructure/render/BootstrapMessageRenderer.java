package org.xine.navigator.presentation.infrastructure.render;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.MessageRenderer;

@FacesRenderer(componentFamily = "javax.faces.Message", rendererType = "javax.faces.Message")
public class BootstrapMessageRenderer extends MessageRenderer {
	private static final Attribute[] ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.MESSAGESMESSAGES);

	@Override
	public void encodeBegin(final FacesContext context, final UIComponent component) throws IOException {
		super.encodeBegin(context, component);
	}

	@Override
	public void encodeEnd(final FacesContext context, final UIComponent component) throws IOException {
		rendererParamsNotNull(context, component);

		if (!shouldEncode(component)) {
			return;
		}

		final boolean mustRender = shouldWriteIdAttribute(component);

		final UIMessage messages = (UIMessage) component;
		final ResponseWriter writer = context.getResponseWriter();
		assert (writer != null);

		final String clientId = ((UIMessage) component).getFor();

		@SuppressWarnings("unchecked")
		final Iterator<FacesMessage> messageIter = getMessageIter(context, clientId, component);

		assert (messageIter != null);

		if (!messageIter.hasNext()) {
			if (mustRender) {
				if ("javax_faces_developmentstage_messages".equals(component.getId())) {
					return;
				}
				writer.startElement("div", component);
				writeIdAttributeIfNecessary(context, writer, component);
				writer.endElement("div");
			}
			return;
		}

		writeIdAttributeIfNecessary(context, writer, component);

		// style is rendered as a passthru attribute
		RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);

		final Map<Severity, List<FacesMessage>> msgs = new HashMap<Severity, List<FacesMessage>>();
		msgs.put(FacesMessage.SEVERITY_INFO, new ArrayList<FacesMessage>()); // Bootstrap
		// info
		msgs.put(FacesMessage.SEVERITY_WARN, new ArrayList<FacesMessage>()); // Bootstrap
		// warning
		msgs.put(FacesMessage.SEVERITY_ERROR, new ArrayList<FacesMessage>()); // Bootstrap
		// error
		msgs.put(FacesMessage.SEVERITY_FATAL, new ArrayList<FacesMessage>()); // Bootstrap
		// error

		while (messageIter.hasNext()) {
			final FacesMessage curMessage = messageIter.next();

			if (curMessage.isRendered() && !messages.isRedisplay()) {
				continue;
			}
			msgs.get(curMessage.getSeverity()).add(curMessage);
		}

		List<FacesMessage> severityMessages = msgs.get(FacesMessage.SEVERITY_FATAL);
		if (severityMessages.size() > 0) {
			encodeSeverityMessages(context, component, messages, FacesMessage.SEVERITY_FATAL, severityMessages);
		}

		severityMessages = msgs.get(FacesMessage.SEVERITY_ERROR);
		if (severityMessages.size() > 0) {
			encodeSeverityMessages(context, component, messages, FacesMessage.SEVERITY_ERROR, severityMessages);
		}

		severityMessages = msgs.get(FacesMessage.SEVERITY_WARN);
		if (severityMessages.size() > 0) {
			encodeSeverityMessages(context, component, messages, FacesMessage.SEVERITY_WARN, severityMessages);
		}

		severityMessages = msgs.get(FacesMessage.SEVERITY_INFO);
		if (severityMessages.size() > 0) {
			encodeSeverityMessages(context, component, messages, FacesMessage.SEVERITY_INFO, severityMessages);
		}
	}

	private void encodeSeverityMessages(final FacesContext facesContext, final UIComponent component,
			final UIMessage uiMessages, final Severity severity, final List<FacesMessage> messages) throws IOException {
		final ResponseWriter writer = facesContext.getResponseWriter();

		String alertSeverityClass = "";

		if (FacesMessage.SEVERITY_INFO.equals(severity)) {
			alertSeverityClass = "alert-info";
		} else if (FacesMessage.SEVERITY_WARN.equals(severity)) {
			alertSeverityClass = ""; // Default alert is a warning
		} else if (FacesMessage.SEVERITY_ERROR.equals(severity)) {
			alertSeverityClass = "alert-danger";
			// alertSeverityClass = "alert-error";
		} else if (FacesMessage.SEVERITY_FATAL.equals(severity)) {
			alertSeverityClass = "alert-danger";
			// alertSeverityClass = "alert-error";
		}

		writer.startElement("div", null);
		writer.writeAttribute("class", "alert " + alertSeverityClass, "alert " + alertSeverityClass);

		for (final FacesMessage msg : messages) {
			final String summary = msg.getSummary() != null ? msg.getSummary() : "";
			final String detail = msg.getDetail() != null ? msg.getDetail() : summary;

			if (uiMessages.isShowSummary()) {
				writer.startElement("strong", component);
				writer.writeText(summary, component, null);
				writer.endElement("strong");
			}

			if (uiMessages.isShowDetail()) {
				writer.writeText(" " + detail, null);
			}
			msg.rendered();
		}
		writer.endElement("div");
	}
}