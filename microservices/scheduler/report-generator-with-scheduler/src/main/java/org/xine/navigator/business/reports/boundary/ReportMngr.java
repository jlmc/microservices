package org.xine.navigator.business.reports.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.xine.navigator.business.reports.entity.Report;

@Stateless
public class ReportMngr {

	@PersistenceContext // (unitName="reports-generator-PU")
	EntityManager em;

	public Report save(final Report report) {
		return this.em.merge(report);
	}


}
