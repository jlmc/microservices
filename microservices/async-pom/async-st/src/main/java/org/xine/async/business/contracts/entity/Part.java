package org.xine.async.business.contracts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

public class Part implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDateTime dueDate;
	private BigDecimal value;

	protected Part() {
	}

	public static Part of(LocalDateTime dueDate, BigDecimal value) {
		final Part part = new Part();
		part.dueDate = dueDate;
		part.value = value;
		return part;
	}

	public LocalDateTime getDueDate() {
		return this.dueDate;
	}

	public BigDecimal getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.dueDate, this.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Part other = (Part) obj;
		if (this.dueDate == null) {
			if (other.dueDate != null) {
				return false;
			}
		} else if (!this.dueDate.equals(other.dueDate)) {
			return false;
		}
		if (this.value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!this.value.equals(other.value)) {
			return false;
		}
		return true;
	}

	public Part plus(BigDecimal plus) {
		return of(LocalDateTime.ofInstant(this.dueDate.atZone(ZoneId.systemDefault()).toInstant(),
				ZoneId.systemDefault()), this.value.add(plus));
	}
}
