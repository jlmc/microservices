package org.xine.async.presentation;

import java.math.BigDecimal;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.xine.async.business.contracts.boundary.ContractFinancial;

@RequestScoped
@Named
public class IndexBean {
	private Double precentage = 0.1D;
	private long duration = 0;

	@Inject
	private ContractFinancial contractFinance;


	public void executeAsyncWithCallable() throws Exception {
		final long currentTimeMillis = System.currentTimeMillis();

		final BigDecimal total = this.contractFinance.ajusterAllContractsAsyncWithCallable(this.precentage);

		final long currentTimeMillis2 = System.currentTimeMillis();

		this.duration = currentTimeMillis2 - currentTimeMillis;

		System.out.printf("END ASYNC >> %d ms %f\n", this.duration, total);
	}

	public void executeAsync() throws Exception {
		final long currentTimeMillis = System.currentTimeMillis();

		final BigDecimal total = this.contractFinance.ajusterAllContractsAsync(this.precentage);

		final long currentTimeMillis2 = System.currentTimeMillis();

		this.duration = currentTimeMillis2 - currentTimeMillis;

		System.out.printf("END ASYNC >> %d ms %f\n", this.duration, total);
	}

	public void execute() {
		final long currentTimeMillis = System.currentTimeMillis();

		final BigDecimal total = this.contractFinance.ajusterAllContracts(this.precentage);

		final long currentTimeMillis2 = System.currentTimeMillis();

		this.duration = currentTimeMillis2 - currentTimeMillis;
		System.out.printf("END >> %d ms %f\n", this.duration, total.doubleValue());
	}

	public Double getPrecentage() {
		return this.precentage;
	}

	public void setPrecentage(Double precentage) {
		this.precentage = precentage;
	}

	public long getDuration() {
		return this.duration;
	}
}
