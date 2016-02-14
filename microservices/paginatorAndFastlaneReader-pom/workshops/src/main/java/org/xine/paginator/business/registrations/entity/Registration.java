package org.xine.paginator.business.registrations.entity;

import java.util.function.BiFunction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@NamedQuery(name = Registration.findAll, query = "SELECT r FROM Registration r")
public class Registration {

    @Id
    @GeneratedValue
    private long id;

    private static final String PREFIX = "org.xine.paginator.business.registrations.entity.Registration.";
    public static final String findAll = PREFIX + "all";

    @XmlTransient
    @Transient
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

    public Registration(final long id) {
        this.id = id;
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

}
