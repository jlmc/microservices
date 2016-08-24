package org.xine.async.business.contracts.boundary;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.xine.async.business.contracts.control.ContractAdjusterFinancial;
import org.xine.async.business.contracts.control.ContractAdjusterTask;
import org.xine.async.business.contracts.entity.Contract;
import org.xine.async.business.contracts.entity.Part;

@Stateless
public class ContractFinancial {
	@PersistenceContext(unitName = "async")
	EntityManager em;

	@Inject
	ContractAdjusterFinancial contractAdjusterFinancial;

	@Resource
	ManagedExecutorService services;

	/**
	 * this solution is not so good because, using this way we will have just
	 * one Transaction.
	 * 
	 * As a consequence we are required to put all the tasks at the end so they
	 * can be persisted
	 */
	public BigDecimal ajusterAllContractsAsyncWithCallable(Double percentage)
			throws InterruptedException, ExecutionException {
		final List<Contract> contracts = this.em
				.createQuery("select distinct c from Contract c join fetch c.parts where 1=1", Contract.class)
				.getResultList();

		final List<Future<BigDecimal>> promisses = new ArrayList<Future<BigDecimal>>();

		for (final Contract contract : contracts) {
			promisses.add(this.services.submit(new ContractAdjusterTask(contract, percentage)));
			// services.invokeAll(null)
		}

		// join all the tasks
		BigDecimal contractsAllTotal = BigDecimal.ZERO;
		while (!promisses.isEmpty()) {
			final List<Future<BigDecimal>> completedPromisses = new ArrayList<>();
			for (final Future<BigDecimal> promisse : promisses) {
				if (promisse.isDone()) {
					final BigDecimal bigDecimal = promisse.get();
					contractsAllTotal = contractsAllTotal.add(bigDecimal);

					completedPromisses.add(promisse);
				}
			}

			promisses.removeAll(completedPromisses);
		}

		return contractsAllTotal;
	}

	public BigDecimal ajusterAllContractsAsync(Double percentage) throws InterruptedException, ExecutionException {
		final List<Contract> contracts = this.em.createQuery("select c from Contract c where 1=1", Contract.class)
				.getResultList();

		final List<Future<BigDecimal>> promisses = new ArrayList<Future<BigDecimal>>();

		for (final Contract contract : contracts) {
			promisses.add(this.contractAdjusterFinancial.ajusterContract(percentage, contract));
		}

		// join all the tasks, although this is not necessary
		BigDecimal contractsAllTotal = BigDecimal.ZERO;
		while (!promisses.isEmpty()) {
			final List<Future<BigDecimal>> completedPromisses = new ArrayList<>();
			for (final Future<BigDecimal> promisse : promisses) {
				if (promisse.isDone()) {
					final BigDecimal bigDecimal = promisse.get();
					contractsAllTotal = contractsAllTotal.add(bigDecimal);

					completedPromisses.add(promisse);
				}
			}

			promisses.removeAll(completedPromisses);
		}

		return contractsAllTotal;
	}


	public BigDecimal ajusterAllContracts(Double percentage) {
		final List<Contract> contracts =
					this.em.createQuery("select c from Contract c where 1=1", Contract.class)
							.getResultList();

		final LocalDateTime now = LocalDateTime.now();

		BigDecimal contractsTotal = BigDecimal.ZERO;

		for (final Contract contract : contracts) {
			BigDecimal sumOfParts = BigDecimal.ZERO;
			for (final Part part : contract.getParts()) {
				if (part.getDueDate().compareTo(now) > 0) {
					part.add(percentage);
				}
				sumOfParts = sumOfParts.add(part.getValue());
			}

			contract.setBalance(sumOfParts);

			contractsTotal = contractsTotal.add(contract.getBalance());

		}

		return contractsTotal;
	}
}
