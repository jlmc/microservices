package org.xine.business.registations.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
public class Workshop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Min(3)
	@Max(100)
	private int capacity;

	public Workshop() {}

	public Workshop(final String name, final int capacity) {
		this.name = name;
		this.capacity = capacity;
	}

	public Workshop(final Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	protected void setId(final Long id) {
		this.id = id;
	}

	public int getCapacity() {
		return this.capacity;
	}

	public void setCapacity(final int capacity) {
		this.capacity = capacity;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Workshop [id=" + this.id + ", name=" + this.name + ", capacity=" + this.capacity + "]";
	}

}
