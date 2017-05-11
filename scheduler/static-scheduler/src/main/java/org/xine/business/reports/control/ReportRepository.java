package org.xine.business.reports.control;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.xine.business.reports.entity.Report;

@Stateless
public class ReportRepository {

	@PersistenceContext
	EntityManager em;

	public Collection<Report> loadReports() {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<Report> criteria = builder.createQuery(Report.class);

		final Root<Report> from = criteria.from(Report.class);
		criteria.select(from);

		final TypedQuery<Report> query = this.em.createQuery(criteria);

		return query.getResultList();
	}

	public Report add(final Report report) {
		return this.em.merge(report);
	}

}
