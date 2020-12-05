package org.xine.async.business.contracts.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity implementation class for Entity: Contract
 *
 */

public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String client;
    private BigDecimal balance;
    private Set<Part> parts = new HashSet<>(0);


    public static Contract of(Long id, String client, BigDecimal balance) {
        final Contract c = new Contract();
        c.id = id;
        c.client = client;
        c.balance = balance;
        return c;
    }

    public Contract() {
        super();
    }
    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getClient() {
        return this.client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Set<Part> getParts() {
        return Collections.unmodifiableSet(this.parts);
    }

    protected void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
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
        final Contract other = (Contract) obj;
        return Objects.equals(this.id, other.id);
    }
   
}
