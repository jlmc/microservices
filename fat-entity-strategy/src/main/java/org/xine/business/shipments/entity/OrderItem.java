package org.xine.business.shipments.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class OrderItem implements Serializable {

	private static final long serialVersionUID = 1L;

	private String product;
	private Integer qty;

	protected OrderItem() {
	}

	public OrderItem(final String product, final Integer qty) {
		this.product = product;
		this.qty = qty;
	}


	public String getProduct() {
		return this.product;
	}

	public Integer getQty() {
		return this.qty;
	}

	protected void setProduct(final String product) {
		this.product = product;
	}

	protected void setQty(final Integer qty) {
		this.qty = qty;
	}

}
