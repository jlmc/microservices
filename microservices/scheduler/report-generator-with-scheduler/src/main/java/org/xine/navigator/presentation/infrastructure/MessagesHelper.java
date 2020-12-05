package org.xine.navigator.presentation.infrastructure;

import java.io.Serializable;

import javax.enterprise.inject.Default;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Default
public class MessagesHelper implements Serializable {

    private static final long serialVersionUID = 1L;

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
