package org.xine.paginator.business.customers.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	protected Customer() {}

	public Customer(final String name) {
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}


	protected void setId(final Long id) {
		this.id = id;
	}

	protected void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Customer [id=" + this.id + ", name=" + this.name + "]";
	}
}
