package org.xine.async.business.contracts.boundary;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.xine.async.business.contracts.entity.Contract;

@Stateless
public class ContractManager {

	@PersistenceContext(unitName = "async")
	EntityManager em;

	public Contract create(Contract contract) {
		return this.em.merge(contract);
	}

	public Contract get(Long id) {
		final CriteriaBuilder builder = this.em.getCriteriaBuilder();
		final CriteriaQuery<Contract> criteriaQuery = builder.createQuery(Contract.class);

		final Root<Contract> contracts = criteriaQuery.from(Contract.class);
		contracts.fetch("parts", JoinType.LEFT);

		criteriaQuery.select(contracts).where(builder.equal(contracts.get("id"), id));

		return this.em.createQuery(criteriaQuery).getSingleResult();
	}
}
