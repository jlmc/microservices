package org.xine.navigator.business.reports.boundary;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.xine.navigator.business.reports.entity.Report;

@Stateless
public class ReportMngr {

	@PersistenceContext // (unitName="reports-generator-PU")
	EntityManager em;

	public Report save(final Report report) {
		if (load(report.getName()) != null) {
			throw new IllegalArgumentException("the Name already in use.");
		}
		return this.em.merge(report);
	}

	public Collection<Report> loadReport(final int pageNum, final int pageSize) {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<Report> criateria = builder.createQuery(Report.class);

		final Root<Report> from = criateria.from(Report.class);
		criateria.select(from);
		// criateria.where(restrictions);

		final TypedQuery<Report> query = this.em.createQuery(criateria);
		final List<Report> resultList = query.getResultList();

		return Collections.unmodifiableList(resultList);
	}

	private Report load(final String name) {
		try {
			final CriteriaBuilder builder = this.em.getCriteriaBuilder();
			final CriteriaQuery<Report> criteria = builder.createQuery(Report.class);

			final Root<Report> from = criteria.from(Report.class);
			criteria.select(from);
			criteria.where(builder.equal(from.get("name"), name));

			return this.em.createQuery(criteria).getSingleResult();
		} catch (final NoResultException e) {
			return null;
		}
	}

}
