package org.xine.navigator.business.resportsmngr.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	protected Report() {
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

}
