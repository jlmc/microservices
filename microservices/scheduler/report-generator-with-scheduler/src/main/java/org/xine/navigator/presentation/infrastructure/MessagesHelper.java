package org.xine.navigator.presentation.infrastructure;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

public class MessagesHelper {

	@Inject
	private FacesContext facesContext;

	public void addMessageFlash(final FacesMessage message) {
		this.facesContext.getExternalContext()
						 .getFlash()
						 .setKeepMessages(true);
		this.facesContext.addMessage(null, message);
	}

	public void addMessage(final FacesMessage message) {
		this.facesContext.addMessage(null, message);
	}


}
