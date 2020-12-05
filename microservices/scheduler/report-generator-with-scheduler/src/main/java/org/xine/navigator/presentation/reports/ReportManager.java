package org.xine.navigator.presentation.reports;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.navigator.business.reports.boundary.ReportMngr;
import org.xine.navigator.business.reports.entity.Report;
import org.xine.navigator.presentation.infrastructure.MessagesHelper;

// @ViewScoped
@Named
@RequestScoped
public class ReportManager implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    MessagesHelper messagesHelper;

    @Inject
    ReportMngr bo;

    private Report report;

    @PostConstruct
    public void initialize() {
        if (this.report == null) {
            this.report = new Report();
        }
    }

    public Object save() {

        try {
            System.out.println(this.report);
            // save the thing

            this.bo.save(this.report);

            this.report = new Report();

            this.messagesHelper.addMessageFlash(new FacesMessage("Report created with sucess."));

            return "/reports/reportList?faces-redirect=true";
        } catch (final Exception e) {
            System.out.println(e.getCause());
            this.messagesHelper.addMessageFlash(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getCause().getMessage(), null));
            return null;
        }
    }

    public Report getReport() {
        return this.report;
    }

    public void setReport(final Report report) {
        this.report = report;
    }
}
