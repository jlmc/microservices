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
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.renderkit.html_basic.MessagesRenderer;

@FacesRenderer(componentFamily = "javax.faces.Messages", rendererType = "javax.faces.Messages")
public class BootstrapMessagesRenderer extends MessagesRenderer {

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

		final UIMessages messages = (UIMessages) component;
		final ResponseWriter writer = context.getResponseWriter();
		assert (writer != null);

		String clientId = ((UIMessages) component).getFor();
		if (clientId == null) {
			if (messages.isGlobalOnly()) {
				clientId = "";
			}
		}

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
			final UIMessages uiMessages, final Severity severity, final List<FacesMessage> messages)
					throws IOException {
		final ResponseWriter writer = facesContext.getResponseWriter();

		String alertSeverityClass = "";

		if (FacesMessage.SEVERITY_INFO.equals(severity)) {
			alertSeverityClass = "alert-info";
		} else if (FacesMessage.SEVERITY_WARN.equals(severity)) {
			// warning
			alertSeverityClass = ""; // Default alert is a warning
		} else if (FacesMessage.SEVERITY_ERROR.equals(severity)) {
			// alertSeverityClass = "alert-error";
			alertSeverityClass = "alert-danger";
		} else if (FacesMessage.SEVERITY_FATAL.equals(severity)) {
			// alertSeverityClass = "alert-error";
			alertSeverityClass = "alert-danger";
		}

		writer.startElement("div", null);
		writer.writeAttribute("class", "alert " + alertSeverityClass, "alert " + alertSeverityClass);
		writer.startElement("a", component);
		writer.writeAttribute("class", "close", "class");
		writer.writeAttribute("data-dismiss", "alert", "data-dismiss");
		writer.writeAttribute("href", "#", "href");
		writer.write("&times;");
		writer.endElement("a");

		writer.startElement("ul", null);

		for (final FacesMessage msg : messages) {
			final String summary = msg.getSummary() != null ? msg.getSummary() : "";
			final String detail = msg.getDetail() != null ? msg.getDetail() : summary;

			writer.startElement("li", component);

			if (uiMessages.isShowSummary()) {
				writer.startElement("strong", component);
				writer.writeText(summary, component, null);
				writer.endElement("strong");
			}

			if (uiMessages.isShowDetail()) {
				writer.writeText(" " + detail, null);
			}

			writer.endElement("li");
			msg.rendered();
		}
		writer.endElement("ul");
		writer.endElement("div");
	}
}