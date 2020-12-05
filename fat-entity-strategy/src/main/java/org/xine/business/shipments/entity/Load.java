package org.xine.business.shipments.entity;

import static org.xine.business.threadlocals.control.ThreadLocalEntityManager.em;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Load {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "LoadItens", joinColumns = @JoinColumn(name = "Load_id") )
    private List<OrderItem> orderItens;

    protected Load() {
        this.orderItens = new ArrayList<OrderItem>();
    }

    public void add(final OrderItem orderItem) {
        this.orderItens.add(orderItem);
    }

    public Long getId() {
        return this.id;
    }

    public List<OrderItem> getOrderItens() {
        return Collections.unmodifiableList(this.orderItens);
    }

    protected void setId(final Long id) {
        this.id = id;
    }

    protected void setOrderItens(final List<OrderItem> orderItens) {
        this.orderItens = orderItens;
    }

    public static class Builder {
        private final Load load;

        public Builder() {
            this.load = new Load();
        }

        public Builder withItem(final String product, final Integer qty) {
            this.load.add(new OrderItem(product, qty));
            return this;
        }

        public Load build() {
            if (this.load.getOrderItens().isEmpty()) {
                throw new IllegalStateException("Cannot create Load with any Item");
            }

            em().persist(this.load);

            return this.load;
        }

    }

    public static Load find(final Long id) {
        return em().find(Load.class, id);
    }


}
