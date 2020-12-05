package org.xine.navigator.presentation.infrastructure.exceptionhandler;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

public class JsfExceptionHandler extends ExceptionHandlerWrapper {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ExceptionHandler wrapped;

    public JsfExceptionHandler(final ExceptionHandler wrapper) {
        this.wrapped = wrapper;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return this.wrapped;
    }

    @Override
    public void handle() throws FacesException {

        // all exception events on the Stack Pool
        final Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();

        boolean remove = false;

        while (events.hasNext()) {
            final ExceptionQueuedEvent event = events.next();
            final ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            // the throws exception
            final Throwable exceptionThrows = context.getException();

            // final BusinessException businessException =
            // getBusinessException(exceptionThrows);

            try {
                if (exceptionThrows instanceof ViewExpiredException) {
                    remove = true;
                    redirect("/");
                }
                /*else if (businessException != null) {
                        remove = true;
                        FacesUtil.addErrorMessage(businessException.getMessage());
                    }*/
                else {
                    remove = true;
                    this.logger.log(Level.SEVERE, "SYSTEM ERROR: " + exceptionThrows.getMessage(), exceptionThrows);
                    redirect("/error.xhtml");
                }
                // we goes to here others exeptions types that we want to
                // treat
            } finally {
                // we just want to remove the handled exception
                if (remove) {
                    events.remove();
                }
            }
        }

        getWrapped().handle();

    }

    /*
    private BusinessException getBusinessException(final Throwable exception) {
        if (exception instanceof BusinessException) {
            return (BusinessException) exception;
        } else if (exception.getCause() != null) {
            return getBusinessException(exception.getCause());
        }
        return null;
    }
     */

    private static void redirect(final String page) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ExternalContext externalContext = facesContext.getExternalContext();
            // - /SalesOrders
            // this will be the finalname defined for the application
            // "/"+ finalName
            final String contextPath = externalContext.getRequestContextPath();

            externalContext.redirect(contextPath + page);

            // at this time the response is complete.
            // we want to prevent onother processing of JSF life cycle, so we
            // goes
            // the faceContext as responseComplete
            facesContext.responseComplete();
        } catch (final IOException e) {
            throw new FacesException(e);
        }

    }

}
