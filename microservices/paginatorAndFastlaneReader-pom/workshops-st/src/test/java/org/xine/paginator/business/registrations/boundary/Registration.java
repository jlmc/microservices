package org.xine.paginator.business.registrations.boundary;

import java.util.function.BiFunction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Copied on purpose from workshops for true decoupling.
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Registration {

	private long id;

	@XmlTransient
	private BiFunction<Boolean, Integer, Integer> taxCalculator;

	private int numberOfDays;
	private int numberOfAttendees;
	private boolean vatIdAvailable;

	private final static int DAILY_PRICE = 300;

	public Registration(final boolean vatIdAvailable, final int numberOfDays, final int numberOfAttendees) {
		this.numberOfDays = numberOfDays;
		this.numberOfAttendees = numberOfAttendees;
		this.vatIdAvailable = vatIdAvailable;
	}

	public Registration() {
	}

	public int getNetPrice() {
		return this.numberOfAttendees * this.numberOfDays * DAILY_PRICE;
	}

	public void setCalculator(final BiFunction<Boolean, Integer, Integer> taxCalculator) {
		this.taxCalculator = taxCalculator;
	}

	public int getTotalPrice() {
		return this.taxCalculator.apply(this.vatIdAvailable, getNetPrice());
	}

	public boolean isVatIdAvailable() {
		return this.vatIdAvailable;
	}

	public long getId() {
		return this.id;
	}

	public int getNumberOfDays() {
		return this.numberOfDays;
	}

	public int getNumberOfAttendees() {
		return this.numberOfAttendees;
	}

}
