package org.xine.navigator.presentation.reports;

import java.io.Serializable;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.navigator.business.reports.boundary.ReportMngr;
import org.xine.navigator.business.reports.entity.Report;

@Named
@ViewScoped
public class ReportList implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    ReportMngr bo;

    private Collection<Report> reports;

    private int pageNum = 0;
    private int pageSize = 10;

    @PostConstruct
    public void initialize() {
        next();

    }

    public Collection<Report> getReports() {
        return this.reports;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(final int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public void next() {
        this.reports = this.bo.loadReport(this.pageNum, this.pageSize);
    }

}
