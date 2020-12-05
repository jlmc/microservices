package org.xine.presentation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.xine.business.shipments.boundary.ShipmentService;
import org.xine.business.shipments.entity.Load;
import org.xine.business.shipments.entity.OrderItem;

//@Named
//@ViewScoped
@Presenter
public class Index implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    ShipmentService bo;

    private String product;
    private Integer qty = 1;

    private List<OrderItem> itens;

    @PostConstruct
    public void onInitialize() {

        this.itens = new ArrayList<>();
    }

    public void addItem() {
        this.itens.add(new OrderItem(this.product, this.qty));
        this.product = null;
        this.qty = 1;
    }

    public String save() {
        @SuppressWarnings("unused")
        final Load.Builder builder = new Load.Builder();
        this.itens.forEach(oi -> {
            this.bo.createLoad(oi.getProduct(), oi.getQty());
        });

        this.itens.clear();
        return null;
    }

    public Integer getQty() {
        return this.qty;
    }

    public String getProduct() {
        return this.product;
    }

    public void setProduct(final String product) {
        this.product = product;
    }

    public void setQty(final Integer qty) {
        this.qty = qty;
    }

    public List<OrderItem> getItens() {
        return this.itens;
    }
}
